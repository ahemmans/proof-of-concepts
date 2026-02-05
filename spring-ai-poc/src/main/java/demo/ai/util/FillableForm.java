package demo.ai.util;

public enum FillableForm {
    FORM8980(true),
    FORM8981(true),
    FORM8985(true),
    FORM8988(true),
    FORM8989(true),
    FORM8984(false),
    FORM14027(false);

    private final boolean fillable;

    FillableForm(boolean fillable) {
        this.fillable = fillable;
    }

    public boolean isFillable() {
        return fillable;
    }

    public String getFormType() {
        return this.name();
    }

    public static FillableForm fromFormType(String formType) {
        try {
            return valueOf(formType.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    public static boolean isFormTypeFillable(String formType) {
        FillableForm form = fromFormType(formType);
        return form != null && form.isFillable();
    }

}
