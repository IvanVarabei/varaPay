document.addEventListener('DOMContentLoaded', function () {
    var pass1 = document.querySelector('#password'),
        pass2 = document.querySelector('#password-check')
    pass1.addEventListener('input', function () {
        this.value != pass2.value ? pass2.setCustomValidity('${client_repeat_password_error}') :
            pass2.setCustomValidity('')
    })
    pass2.addEventListener('input', function (e) {
        this.value != pass1.value ? this.setCustomValidity('${client_repeat_password_error}') :
            this.setCustomValidity('')
    })
})