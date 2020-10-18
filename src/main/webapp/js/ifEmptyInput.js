function validateForm() {
    var x = document.forms["myForm"]["fname"].value;
    if (x == "") {
        alert("Необходимо ввести имя");
        return false;
    }
}