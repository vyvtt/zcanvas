var xmlDoc;
var xslDoc;
var xmlStr;

function loadPreviewImg(event) {
    var preview = document.getElementById('preview');
    preview.style.display = 'inline-block';
    preview.src = URL.createObjectURL(event.target.files[0]);
    var palette = document.getElementById('palette');
    palette.style.display = 'none';
}

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

function initDocument(canvasXML, realPath) {
    console.log('init doc');
    
    var parser = new DOMParser();
    xmlDoc = parser.parseFromString(canvasXML, "application/xml");

    var xslUrl = realPath + "/document/home.xsl";
    xslDoc = loadXML(xslUrl);
    
    xmlStr = canvasXML;
}

function renderCanvas(canvasXML, categoryId, realPath) {
    var parser = new DOMParser();
    var xmlDoc = parser.parseFromString(canvasXML, "application/xml");

    var xslUrl = realPath + "/document/home.xsl";

    var xslDoc = loadXML(xslUrl);

    console.log('input xml: ' + canvasXML);
    console.log('load xsl file: ' + xslDoc);
    console.log('load xml str : ' + xmlDoc);
    console.log('category: ' + categoryId);

    if (document.implementation && document.implementation.createDocument)
    {
        console.log('in XSLTProcessor');
        xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(xslDoc);
        xsltProcessor.setParameter(null, "categoryId", categoryId);
        resultDocument = xsltProcessor.transformToFragment(xmlDoc, document);

        console.log(resultDocument);

        var myNode = document.getElementById("example");
        while (myNode.firstChild) {
            myNode.removeChild(myNode.firstChild);
        }
//        document.getElementById("example").innerHTML = '';
//        document.getElementById("example").appendChild(resultDocument);
        myNode.appendChild(resultDocument);
    }
}

function renderCanvasNew(categoryId) {

    console.log('xmlStr : ' + xmlStr);
    console.log('xsl : ' + xslDoc);
    console.log('xml : ' + xmlDoc);
    console.log('category: ' + categoryId);

    if (document.implementation && document.implementation.createDocument)
    {
        console.log('in XSLTProcessor');
        xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(xslDoc);
//        xsltProcessor.setParameter("http://canvas.com/vyvtt", "categoryId", categoryId);
        xsltProcessor.setParameter(null, "categoryId", categoryId);
        resultDocument = xsltProcessor.transformToFragment(xmlDoc, document);

        console.log(resultDocument);

//        var myNode = document.getElementById("example");
//        while (myNode.firstChild) {
//            myNode.removeChild(myNode.firstChild);
//        }
        document.getElementById("example").innerHTML = '';
        document.getElementById("example").appendChild(resultDocument);
//        myNode.appendChild(resultDocument);
    }
}