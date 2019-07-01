(function () {

    var homeModel = {
        listInputColors: [],
        listCategories: [],

        xmlLocation: null,
        xmlCanvas: null,

        xslLocation: null,
        xslHome: null
    };
    var homeView = {
        init: function () {
            octopus.getLocations();

            var form = document.getElementById('form');
            form.addEventListener('submit', function (evt) {
                evt.preventDefault();

                var spanLoading = document.getElementById("span-loading");
                form.style.display = "none";
                spanLoading.style.display = "block";

                var data = new FormData(form);
                octopus.submitForm(data);

            });
        },
        renderLocation: function () {
            if (document.implementation && document.implementation.createDocument)
            {
                xsltProcessor = new XSLTProcessor();
                xsltProcessor.importStylesheet(octopus.getXslLocation());
                resultDocument = xsltProcessor.transformToFragment(octopus.getXmlLocation(), document);

                var myNode = document.getElementById("test");
                while (myNode.firstChild) {
                    myNode.removeChild(myNode.firstChild);
                }
                myNode.appendChild(resultDocument);
            }
        },
        renderPalatte: function () {
            var spanPalette = document.getElementById("palette");
            spanPalette.style.display = "inline-block";

            while (spanPalette.firstChild) {
                spanPalette.removeChild(spanPalette.firstChild);
            }

            var listColor = octopus.getListColors();
            for (var i = 0; i < listColor.length; i++) {
                var divPalette = document.createElement("div");
                divPalette.setAttribute("style", "background-color:" + listColor[i].textContent + ";");
                spanPalette.appendChild(divPalette);
            }
        },
        renderCategories: function () {
            var divCategoryWrap = document.getElementById("div-category-wrap");
            while (divCategoryWrap.firstChild) {
                divCategoryWrap.removeChild(divCategoryWrap.firstChild);
            }

            var cDiv = document.createElement("div");
            cDiv.setAttribute("class", "div-category");

            var cRadio = document.createElement("input");
            cRadio.setAttribute("type", "radio");
            cRadio.setAttribute("id", "c0");
            cRadio.setAttribute("name", "rbCategory");
            cRadio.setAttribute("checked", "checked");

            var cLabel = document.createElement("label");
            cLabel.setAttribute("for", "c0");

            var cP = document.createElement("p");
            cP.innerHTML = "Tất cả";

            cLabel.addEventListener('click', function () {
                homeView.renderCanvasResult('0');
            });

            cLabel.appendChild(cP);
            cDiv.appendChild(cRadio);
            cDiv.appendChild(cLabel);
            divCategoryWrap.appendChild(cDiv);

            var listCategory = octopus.getListCategories();
            var inputList = Array.prototype.slice.call(listCategory);
            
            inputList.forEach(function (category) {
                var name = category.getElementsByTagName("name")[0].textContent;
                var id = category.getElementsByTagName("id")[0].textContent;

                var cDiv = document.createElement("div");
                cDiv.setAttribute("class", "div-category");

                var cRadio = document.createElement("input");
                cRadio.setAttribute("type", "radio");
                cRadio.setAttribute("id", "c" + id);
                cRadio.setAttribute("name", "rbCategory");

                var cLabel = document.createElement("label");
                cLabel.setAttribute("for", "c" + id);

                var cP = document.createElement("p");
                cP.innerHTML = name;

                cLabel.addEventListener('click', function () {
                    homeView.renderCanvasResult(id);
                });

                cLabel.appendChild(cP);
                cDiv.appendChild(cRadio);
                cDiv.appendChild(cLabel);
                divCategoryWrap.appendChild(cDiv);
                
            });

//            for (var i = 0; i < listCategory.length; i++) {
//                var name = listCategory[i].getElementsByTagName("name")[0].textContent;
//                var id = listCategory[i].getElementsByTagName("id")[0].textContent;
//
//                var cDiv = document.createElement("div");
//                cDiv.setAttribute("class", "div-category");
//
//                var cRadio = document.createElement("input");
//                cRadio.setAttribute("type", "radio");
//                cRadio.setAttribute("id", "c" + id);
//                cRadio.setAttribute("name", "rbCategory");
//
//                var cLabel = document.createElement("label");
//                cLabel.setAttribute("for", "c" + id);
//
//                var cP = document.createElement("p");
//                cP.innerHTML = name;
//
//                cLabel.addEventListener('click', function () {
//                    console.log(id);
//                    homeView.renderCanvasResult(id);
//                });
//
//                cLabel.appendChild(cP);
//                cDiv.appendChild(cRadio);
//                cDiv.appendChild(cLabel);
//                divCategoryWrap.appendChild(cDiv);
//            }
        },
        renderCanvasResult: function (categoryId) {
            if (document.implementation && document.implementation.createDocument)
            {
                xsltProcessor = new XSLTProcessor();
                xsltProcessor.importStylesheet(octopus.getXslHome());
                xsltProcessor.setParameter(null, "categoryId", categoryId + '');
                resultDocument = xsltProcessor.transformToFragment(octopus.getXmlCanvas(), document);

                document.getElementById("result").innerHTML = '';
                document.getElementById("result").appendChild(resultDocument);

                var form = document.getElementById('form');
                var spanLoading = document.getElementById("span-loading");
                form.style.display = "block";
                spanLoading.style.display = "none";
            }
        }
    };

    var octopus = {
        init: function () {
            octopus.getXsl();
            homeView.init();
        },
        getXsl: function () {
            var xslUrl = null;

            xslUrl = "/ZCanvas/document/home-location.xsl";
            homeModel.xslLocation = loadXML(xslUrl);

            xslUrl = "/ZCanvas/document/home.xsl";
            homeModel.xslHome = loadXML(xslUrl);
        },
        getLocations: function () {
            var url = "ProcessServlet";
            var param = {
                btAction: 'initLocation'
            };
            sendGetRequest(url, param, function (response) {
                var xmlDoc = response.responseXML;
                homeModel.xmlLocation = xmlDoc;
                // render list location
                homeView.renderLocation();
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

                homeModel.listInputColors = str.getElementsByTagName("inputColor");
                homeView.renderPalatte();

                homeModel.listCategories = str.getElementsByTagName("category");
                homeView.renderCategories();

                homeModel.xmlCanvas = str.getElementsByTagName('canvases')[0];
                homeView.renderCanvasResult(0);
            });
        },
        getXslLocation: function () {
            return homeModel.xslLocation;
        },
        getXslHome: function () {
            return homeModel.xslHome;
        },
        getXmlLocation: function () {
            return homeModel.xmlLocation;
        },
        getXmlCanvas: function () {
            return homeModel.xmlCanvas;
        },
        getListColors: function () {
            return homeModel.listInputColors;
        },
        getListCategories: function () {
            return homeModel.listCategories;
        }
    };

    octopus.init();
}());
