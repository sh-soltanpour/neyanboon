var apiUrl = "http://localhost:8081"
var data = JSON.stringify({
  "name": "Android"
});

var xhr = new XMLHttpRequest();
xhr.withCredentials = false;

xhr.addEventListener("readystatechange", function () {
  if (this.readyState === 4) {
    console.log(this.responseText);
  }
});

xhr.open("POST", apiUrl + "/users/skills");
xhr.setRequestHeader("Content-Type", "application/json");

xhr.send(data);