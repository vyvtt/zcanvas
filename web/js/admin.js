
window.onload = function () {
    console.log('collapse');
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

function tryAgain() {
    var xslUrl = null;

    xslUrl = "/ZCanvas/document/categories.xml";
    var category = loadXML(xslUrl);
    console.log('category load xml: ' + category);
    console.log(category);
    
    xslUrl = "/ZCanvas/document/admin-test.xsl";
    var xsl = loadXML(xslUrl);
    
    if (document.implementation && document.implementation.createDocument)
    {
        console.log('in XSLTProcessor');
        xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(xslDoc);
        xsltProcessor.setParameter(null, "categoryInput", category);
//        xsltProcessor.setParameter(null, "a", xmlCategories);
        resultDocument = xsltProcessor.transformToFragment(xmlDoc, document);
        console.log(resultDocument);
        document.getElementById("example").appendChild(resultDocument);
    }
}

//function getXMLHttpObject() {
//    var xmlHttp = null;
//    try {
//        xmlHttp = new XMLHttpRequest();
//    } catch (e) {
//        try {
//            new ActiveXObject("Msxml2.XMLHTTP");
//        } catch (e) {
//            new ActiveXObject("Microsoft.XMLHTTP");
//        }
//    }
//    return xmlHttp;
//}

//function loadXML(filePath) {
//    xmlHttp = getXMLHttpObject();
//    if (xmlHttp == null) {
//        alert('Browser not supprt AJAX');
//        return;
//    }
//    xmlHttp.open("GET", filePath, false);
//    xmlHttp.send(null);
//    return xmlHttp.responseXML;
//}

function displayResult(realPath, xmlString, xmlCategories) {
    console.log('real path ' + realPath);

    var xslUrl = realPath + "/document/admin-test.xsl";
//    var xmlUrl = realPath + "/admin.xsl";

    console.log('xsl path: ' + xslUrl);

    var xslDoc = loadXML(xslUrl);
    console.log('load file: ' + xslDoc);

    console.log('xml string: ' + xmlString);
    var parser = new DOMParser();
    var xmlDoc = parser.parseFromString(xmlString, "application/xml");

    if (document.implementation && document.implementation.createDocument)
    {
        console.log('in XSLTProcessor');
        xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(xslDoc);
        xsltProcessor.setParameter(null, "categoriesFile", "WEB-INF/document/categories.xml");
        xsltProcessor.setParameter(null, "a", xmlCategories);
        resultDocument = xsltProcessor.transformToFragment(xmlDoc, document);
        console.log(resultDocument);
        document.getElementById("example").appendChild(resultDocument);
    }
}

function test(realPath) {
    alert('in test');
    console.log(realPath);

    var xslUrl = realPath + "/admin.xsl";
    var xmlUrl = realPath + "/admin.xsl";

    console.log(xslUrl);

    //    alert('in');
//
//    console.log('xmlString: ' + xmlString);
//    console.log('xslString: ' + xslString);
//
//    xsl = loadXMLDoc("../document/admin.xsl");
//
//    console.log('load file: ' + xsl);
//
//    var parser = new DOMParser();
//    var xmlDoc = parser.parseFromString(xmlString, "application/xml");
//    var xslDoc = parser.parseFromString(xslString, "application/xml");
//
//    console.log(xmlDoc);
//    console.log(xslDoc);
//
//    if (document.implementation && document.implementation.createDocument)
//    {
//        console.log('in Chrome');
//        xsltProcessor = new XSLTProcessor();
//        xsltProcessor.importStylesheet(xslDoc);
//        xsltProcessor.setParameter(null, "categoriesFile", "WEB-INF/document/categories.xml");
//        resultDocument = xsltProcessor.transformToFragment(xmlDoc, document);
//        console.log(resultDocument);
//        document.getElementById("example").appendChild(resultDocument);
//    }

}