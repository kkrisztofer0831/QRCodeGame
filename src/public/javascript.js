function terkepfull() {
    document.getElementById("terkep").style.width = "100%";
    document.getElementById("elolista").style.width = "0%";
    document.getElementById("elolista_cont").style.display = "none";
    document.getElementById("ranglista").style.width = "0%";
    document.getElementById("ranglista_cont").style.display = "none";
}
function elolista() {
    document.getElementById("terkep").style.width = "70%";    
    document.getElementById("ranglista").style.width = "0%";
    document.getElementById("ranglista_cont").style.display = "none";
    document.getElementById("elolista").style.width = "30%";
    document.getElementById("elolista_cont").style.display = "inline";
}
function ranglista() {
    document.getElementById("terkep").style.width = "70%";
    document.getElementById("elolista").style.width = "0%";
    document.getElementById("elolista_cont").style.display = "none";
    document.getElementById("ranglista").style.width = "30%";
    document.getElementById("ranglista_cont").style.display = "inline";
}
