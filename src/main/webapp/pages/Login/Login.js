/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Page.onReady = function() {
    Page.Widgets.UserDetailLiveForm1.formfields.confirmPassword.setValidators([confirmPasswordEval]);

    Page.Widgets.UserDetailLiveForm1.formfields.confirmPassword.observeOn([
        'password'
    ]);
};


// Password and Confirm Password Validators
function confirmPasswordEval(field, form) {
    if (field.value && form.formfields.password.value !=
        field.value) {
        return {
            errorMessage: "Password & Confirm Password do not match"
        };
    }
}