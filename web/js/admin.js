(function () {

    var adminModel = {
    };
    var adminView = {
        init: function () {

            document.getElementById("li-add").addEventListener('click', adminView.renderAdd, false);
            document.getElementById("li-edit").addEventListener('click', adminView.renderEdit, false);
            document.getElementById("li-crawl").addEventListener('click', adminView.renderCrawl, false);
            document.getElementById("lbCrawl").addEventListener('click', octopus.crawlData, false);

            // collapse
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

        },
        renderLocation: function () {
            console.log('render');
            var scripts = document.getElementsByTagName("script");
            src = scripts[scripts.length - 1].src;
            console.log(src);
            if (document.implementation && document.implementation.createDocument)
            {
                xsltProcessor = new XSLTProcessor();
                xsltProcessor.importStylesheet(octopus.getXslAdmin());
                resultDocument = xsltProcessor.transformToFragment(octopus.getXmlAdmin(), document);
                console.log(resultDocument);

                var myNode = document.getElementById("admin");
                while (myNode.firstChild) {
                    myNode.removeChild(myNode.firstChild);
                }
                myNode.appendChild(resultDocument);
            }
        },
        renderCrawl: function () {
            console.log('render crwal');

            document.getElementById("li-crawl").className = "nav-active";
            document.getElementById("li-edit").className = "";
            document.getElementById("li-add").className = "";

            document.getElementById("item-crawl").style.display = 'block';
            document.getElementById("item-edit").style.display = 'none';
            document.getElementById("item-add").style.display = 'none';
        },
        renderAdd: function () {
            console.log('render add');
            document.getElementById("li-crawl").className = "";
            document.getElementById("li-edit").className = "";
            document.getElementById("li-add").className = "nav-active";

            document.getElementById("item-crawl").style.display = 'none';
            document.getElementById("item-edit").style.display = 'none';
            document.getElementById("item-add").style.display = 'block';
        },
        renderEdit: function () {
            console.log('render edit');
            document.getElementById("li-crawl").className = "";
            document.getElementById("li-edit").className = "nav-active";
            document.getElementById("li-add").className = "";

            document.getElementById("item-crawl").style.display = 'none';
            document.getElementById("item-edit").style.display = 'block';
            document.getElementById("item-add").style.display = 'none';
        }
    };

    var octopus = {
        init: function () {
            adminView.init();
            adminView.renderEdit();
        },
        crawlData: function () {
            console.log('crawl');

            var lbCrawl = document.getElementById("lbCrawl");
            var imgCrawl = document.getElementById("imgCrawl");
            lbCrawl.style.display = 'none';
            imgCrawl.style.display = 'block';

            var url = "ProcessServlet";
            var param = {
                btAction: 'crawl'
            };
            sendGetRequest(url, param, function (response) {
                // done
                lbCrawl.style.display = 'block';
                imgCrawl.style.display = 'none';
            });
        },
        submitForm: function (data) {

            // Display the key/value pairs
            for (var pair of data.entries()) {
                console.log(pair[0] + ' = ' + pair[1]);
            }

            var url = "ProcessServlet";
            sendMultiPartForm(url, data, function (response) {
                var str = response.responseXML;

                homeModel.totalResult = str.getElementsByTagName("total")[0].innerHTML;

                homeModel.listInputColors = str.getElementsByTagName("inputColor");
                homeView.renderPalatte();

                homeModel.listCategories = str.getElementsByTagName("category");
                homeView.renderCategories();

                homeModel.xmlCanvas = str.getElementsByTagName('canvases')[0];
                homeView.renderCanvasResult(0);
            });
        }
    };
    octopus.init();
}());

//function tryAgain() {
//    var xslUrl = null;
//
//    xslUrl = "/ZCanvas/document/categories.xml";
//    var category = loadXML(xslUrl);
//    console.log('category load xml: ' + category);
//    console.log(category);
//
//    xslUrl = "/ZCanvas/document/admin-test.xsl";
//    var xsl = loadXML(xslUrl);
//
//    if (document.implementation && document.implementation.createDocument)
//    {
//        console.log('in XSLTProcessor');
//        xsltProcessor = new XSLTProcessor();
//        xsltProcessor.importStylesheet(xslDoc);
//        xsltProcessor.setParameter(null, "categoryInput", category);
////        xsltProcessor.setParameter(null, "a", xmlCategories);
//        resultDocument = xsltProcessor.transformToFragment(xmlDoc, document);
//        console.log(resultDocument);
//        document.getElementById("example").appendChild(resultDocument);
//    }
//}
//
//function displayResult(realPath, xmlString, xmlCategories) {
//    console.log('real path ' + realPath);
//
//    var xslUrl = realPath + "/document/admin-test.xsl";
////    var xmlUrl = realPath + "/admin.xsl";
//
//    console.log('xsl path: ' + xslUrl);
//
//    var xslDoc = loadXML(xslUrl);
//    console.log('load file: ' + xslDoc);
//
//    console.log('xml string: ' + xmlString);
//    var parser = new DOMParser();
//    var xmlDoc = parser.parseFromString(xmlString, "application/xml");
//
//    if (document.implementation && document.implementation.createDocument)
//    {
//        console.log('in XSLTProcessor');
//        xsltProcessor = new XSLTProcessor();
//        xsltProcessor.importStylesheet(xslDoc);
//        xsltProcessor.setParameter(null, "categoriesFile", "WEB-INF/document/categories.xml");
//        xsltProcessor.setParameter(null, "a", xmlCategories);
//        resultDocument = xsltProcessor.transformToFragment(xmlDoc, document);
//        console.log(resultDocument);
//        document.getElementById("example").appendChild(resultDocument);
//    }
//}
//
//function test(realPath) {
//    alert('in test');
//    console.log(realPath);
//
//    var xslUrl = realPath + "/admin.xsl";
//    var xmlUrl = realPath + "/admin.xsl";
//
//    console.log(xslUrl);
//
//    //    alert('in');
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

//}