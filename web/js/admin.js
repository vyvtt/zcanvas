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
            console.log('render location');
            var scripts = document.getElementsByTagName("script");
            src = scripts[scripts.length - 1].src;
            if (document.implementation && document.implementation.createDocument)
            {
                xsltProcessor = new XSLTProcessor();
                xsltProcessor.importStylesheet(octopus.getXslAdmin());
                resultDocument = xsltProcessor.transformToFragment(octopus.getXmlAdmin(), document);

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
        }
//        submitForm: function (data) {
//
//            // Display the key/value pairs
//            console.log('Submit form with param:');
//            for (var pair of data.entries()) {
//                console.log(pair[0] + ' = ' + pair[1]);
//            }
//
//            var url = "ProcessServlet";
//            sendMultiPartForm(url, data, function (response) {
//                var str = response.responseXML;
//
//                homeModel.totalResult = str.getElementsByTagName("total")[0].innerHTML;
//
//                homeModel.listInputColors = str.getElementsByTagName("inputColor");
//                homeView.renderPalatte();
//
//                homeModel.listCategories = str.getElementsByTagName("category");
//                homeView.renderCategories();
//
//                homeModel.xmlCanvas = str.getElementsByTagName('canvases')[0];
//                homeView.renderCanvasResult(0);
//            });
//        }
    };
    octopus.init();
}());