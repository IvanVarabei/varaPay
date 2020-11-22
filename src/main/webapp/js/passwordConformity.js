function checkPasswordsConformity(firstPasswordSelector, secondPasswordSelector, errorMessage) {
    document.addEventListener('DOMContentLoaded', function () {
        let pass1 = document.querySelector(firstPasswordSelector),
            pass2 = document.querySelector(secondPasswordSelector);
        pass1.addEventListener('input', function () {
            this.value !== pass2.value ? pass2.setCustomValidity(errorMessage) :
                pass2.setCustomValidity('')
        })
        pass2.addEventListener('input', function (e) {
            this.value !== pass1.value ? this.setCustomValidity(errorMessage) :
                this.setCustomValidity('')
        })
    })
}