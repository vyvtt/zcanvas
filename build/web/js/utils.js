var slideIndex = 1;

var myTimer;

window.addEventListener("load", function () {
    showSlides(slideIndex);
    myTimer = setInterval(function () {
        plusSlides(1);
    }, 4000);
});

// NEXT AND PREVIOUS CONTROL
function plusSlides(n) {
    clearInterval(myTimer);
    if (n < 0) {
        showSlides(slideIndex -= 1);
    } else {
        showSlides(slideIndex += 1);
    }
    if (n === -1) {
        myTimer = setInterval(function () {
            plusSlides(n + 2);
        }, 4000);
    } else {
        myTimer = setInterval(function () {
            plusSlides(n + 1);
        }, 4000);
    }
}

//Controls the current slide and resets interval if needed
function currentSlide(n) {
    clearInterval(myTimer);
    myTimer = setInterval(function () {
        plusSlides(n + 1);
    }, 4000);
    showSlides(slideIndex = n);
}

function showSlides(n) {
    var i;
    var slides = document.getElementsByClassName("mySlides");
    if (n > slides.length) {
        slideIndex = 1;
    }
    if (n < 1) {
        slideIndex = slides.length;
    }
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    slides[slideIndex - 1].style.display = "block";
}

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getXMLHttpObject() {
    var xmlHttp = null;
    try {
        xmlHttp = new XMLHttpRequest();
    } catch (e) {
        try {
            new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    return xmlHttp;
}

function sendGetRequest(url, param, responseCallback) {
    var xhttp = getXMLHttpObject();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            responseCallback(this);
        }
    };
    url = url + "?" + getParam(param);
    xhttp.open("GET", url, true);
    xhttp.send();
}

//function sendPostRequest(url, param, responseCallback) {
//    var xhttp = getXMLHttpObject();
//    xhttp.onreadystatechange = function () {
//        if (this.readyState == 4 && this.status == 200) {
//            responseCallback(this);
//        }
//    };
//    xhttp.open("POST", url, true);
//    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
//    var param = getParam(param);
//    xhttp.send(param);
//}

function sendMultiPartForm(url, data, responseCallback) {
    var xhttp = getXMLHttpObject();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            responseCallback(this);
        }
    };
    xhttp.open("POST", url, true);
    xhttp.send(data);
}

function getParam(param) {
    var paramsStr = "";

    for (var prop in param) {
        if (isArray(param[prop])) {
            paramsStr = paramsStr + getParamArray(prop, param[prop]);
        } else {
            paramsStr = paramsStr + prop + "=" + param[prop] + "&";
        }
    }

    if (paramsStr[paramsStr.length - 1] === '&') {
        paramsStr = paramsStr.substring(0, paramsStr.length - 1);
    }

    return paramsStr;
}

function getParamArray(key, arrayValue) {
    var paramArray = "";

    arrayValue.forEach(function (item, index) {
        paramArray = paramArray + key + "=" + item + "&";
    });

    return paramArray;
}

function isArray(value) {
    return value && typeof value === 'object' && value.constructor === Array;
}

function loadXML(filePath) {
    xmlHttp = getXMLHttpObject();
    if (xmlHttp == null) {
        alert('Browser not supprt AJAX');
        return;
    }
    xmlHttp.open("GET", filePath, false);
    xmlHttp.send(null);
    return xmlHttp.responseXML;
}

function loadPreviewImg(event) {
    var spanInput = document.getElementById('span-type-image');
    spanInput.style.display = 'none';

    var spanAll = document.getElementById('span-preview-image');
    spanAll.style.display = 'block';

    var divClose = document.getElementById('closePreview');
    divClose.style.display = 'block';
    console.log(divClose);

    var previewImage = document.getElementById('previewImage');
    previewImage.style.display = 'inline-block';
    previewImage.src = URL.createObjectURL(event.target.files[0]);

    var palette = document.getElementById('palette');
    palette.style.display = 'none';

    var previewColor = document.getElementById('previewColor');
    previewColor.style.display = 'none';
}

function loadPreviewColor(element) {
    element.className = "change";

    var previewColor = document.getElementById('previewColor');
    previewColor.style.display = 'inline-block';
    previewColor.style.background = element.value;
    previewColor.style.backgroundColor = element.value;

    var previewImage = document.getElementById('previewImage');
    previewImage.style.display = 'none';

    var palette = document.getElementById('palette');
    palette.style.display = 'none';
}

function colorOnChange(element) {
    element.className = "change";
}

function validateForm() {
    var mRadio = document.getElementsByName("rbLocation");
    var mFile = document.forms["mForm"]["mFile"].value;
//    var mColor = document.forms["mForm"]["mColor"];
    var mColor = document.getElementsByName("mColor");
    var errLocation = document.getElementById("errLocation");
    var errImage = document.getElementById("errImage");

    console.log(mFile);
    console.log(mColor);

    var validRadio = false;
    var validImg = false;
    errImage.innerHTML = '';
    errLocation.innerHTML = '';

    // check radios
    var i = 0;
    while (!validRadio && i < mRadio.length) {
        if (mRadio[i].checked)
            validRadio = true;
        i++;
    }

    if (document.getElementById('type1Image').checked) {
        // validate img
        if (mFile.length > 0) {
            var allowed_extensions = new Array("jpg", "png");
            var file_extension = mFile.split('.').pop().toLowerCase();

            for (var i = 0; i < allowed_extensions.length; i++) {
                if (allowed_extensions[i] == file_extension) {
                    validImg = true;
                }
            }
            if (!validImg) {
                errImage.innerHTML = 'Only image file (.jpg or .png) allowed!';
            }
        } else {
            // empty img
            errImage.innerHTML = 'Please choose an image!';
        }
    } else {
        // validate color
//        var change = mColor.className;
//        console.log('class name: ' + change);
//        if (change == 'change') {
//            validImg = true;
//        } else {
//            errImage.innerHTML = 'Please choose a color!';
//        }
        // new color validator
        while (!validImg && i < mColor.length) {
            if (mColor[i].checked) {
                validImg = true;
                break;
            }
            i++;
        }
        if (!validImg) {
            errImage.innerHTML = 'Please choose a color!';
        }
    }

    if (!validRadio) {
        errLocation.innerHTML = 'Please choose at least one location!';
    }

    if (validImg && validRadio) {
        return true;
    }

    return false;
}