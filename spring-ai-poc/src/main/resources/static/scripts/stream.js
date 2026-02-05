    const chatForm = document.getElementById('chat-form');
    const messageInput = document.getElementById('message-input');
    const appInput = document.getElementById('app-input');
    const chatMessages = document.getElementById('chat-messages');

    chatForm.addEventListener('submit', async function(e) {
        e.preventDefault();
        const message = messageInput.value;
        const app = appInput.value;
        if (!message) return;

        // Add user message to chat
        addMessageToChat('User', message);

        // Clear input
        messageInput.value = '';

        // Fetch streaming data from the server
        try {
            const params = getQueryParams();
            if (!params.app) {
             params.app = appInput.value;
           }
            const response = await fetchStreamWithRetry('/ai-poc/stream?message=' + encodeURIComponent(message) + '&app=' + encodeURIComponent(params.app));
            const reader = response.body.getReader();
            let botMessageElement = addMessageToChat('Bot', '');
            let contentElement = botMessageElement.querySelector('.message-content');
            await processStream(reader, contentElement);
        } catch (error) {
            console.error('Error fetching chatbot response:', error);
            addMessageToChat('System', 'An error occurred while fetching the response. Please try again.');
        }
    });

    async function fetchStreamWithRetry(url, retries = 3) {
        for (let i = 0; i < retries; i++) {
            try {
                const response = await fetch(url);
                if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                return response;
            } catch (e) {
                console.error(`Attempt ${i + 1} failed: ${e.message}`);
                if (i === retries - 1) throw e;
                await new Promise(resolve => setTimeout(resolve, 1000)); // Wait 1 second before retrying
            }
        }
    }

    async function processStream(reader, contentElement) {
        const decoder = new TextDecoder("utf-8");
        try {
            while (true) {
                const { done, value } = await reader.read();
                if (done) break;
                contentElement.innerHTML += decoder.decode(value, { stream: true });
                chatMessages.scrollTop = chatMessages.scrollHeight;
            }
        } catch (error) {
            console.error('Error processing stream:', error);
            contentElement.innerHTML += '<br>[Error: Stream interrupted. Please try again.]';
        }
    }

    function addMessageToChat(sender, content) {
        const messageElement = document.createElement('div');
        messageElement.className = `${sender.toLowerCase()}-message ${sender === 'User' ? 'bg-blue-100' : 'bg-gray-100'} p-3 rounded-lg`;
        messageElement.innerHTML = `
            <div class="font-bold ${sender === 'User' ? 'text-blue-600' : 'text-green-600'}">${sender}:</div>
            <div class="message-content">${content}</div>
        `;
        chatMessages.appendChild(messageElement);
        chatMessages.scrollTop = chatMessages.scrollHeight;
        return messageElement;
    }
    
    function getQueryParams() {
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        
        const paramsObject = {};
        for (const [key, value] of urlParams) {
            paramsObject[key] = value;
        }
        
        return paramsObject;
    }