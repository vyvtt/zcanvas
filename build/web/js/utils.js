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
    var preview = document.getElementById('preview');
    preview.style.display = 'inline-block';
    preview.src = URL.createObjectURL(event.target.files[0]);
    var palette = document.getElementById('palette');
    palette.style.display = 'none';
}

function validateForm() {
    var mRadio = document.getElementsByName("rbLocation");
    var mFile = document.forms["mForm"]["mFile"].value;
    var errLocation = document.getElementById("errLocation");
    var errImage = document.getElementById("errImage");

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

    // check image
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

    if (!validRadio) {
        errLocation.innerHTML = 'Please choose at least one location!';
    }

    if (validImg && validRadio) {
        return true;
    }
    
    return false;
}