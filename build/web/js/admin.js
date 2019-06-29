/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = function () {
    console.log('onload window');
    
    var coll = document.getElementsByClassName("collapsible");
    var i;

    for (i = 0; i < coll.length; i++) {
        coll[i].addEventListener("click", function () {
            this.classList.toggle("active");
            var content = this.nextElementSibling;
            if (content.style.display === "block") {
                content.style.display = "none";
            } else {
                content.style.display = "block";
            }
        });
    }
};

function loadXMLDoc(filename)
{
    if (window.ActiveXObject)
    {
        xhttp = new ActiveXObject("Msxml2.XMLHTTP");
    } else
    {
        xhttp = new XMLHttpRequest();
    }
    xhttp.open("GET", filename, false);
    try {
        xhttp.responseType = "msxml-document";
    } catch (err) {
        console.log(err);
    } // Helping IE11
    xhttp.send("");
    return xhttp.responseXML;
}


function displayResult(xmlString, xslString) {
    alert('in');

    console.log('xmlString: ' + xmlString);
    console.log('xslString: ' + xslString);

    xsl = loadXMLDoc("../document/admin.xsl");

    console.log('load file: ' + xsl);

    var parser = new DOMParser();
    var xmlDoc = parser.parseFromString(xmlString, "application/xml");
    var xslDoc = parser.parseFromString(xslString, "application/xml");

    console.log(xmlDoc);
    console.log(xslDoc);

    if (document.implementation && document.implementation.createDocument)
    {
        console.log('in Chrome');
        xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(xslDoc);
        xsltProcessor.setParameter(null, "categoriesFile", "WEB-INF/document/categories.xml");
        resultDocument = xsltProcessor.transformToFragment(xmlDoc, document);
        console.log(resultDocument);
        document.getElementById("example").appendChild(resultDocument);
    }
}