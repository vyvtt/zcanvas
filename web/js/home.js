(function () {

    var homeModel = {
        listInputColors: [],
        listCategories: [],
        totalResult: null,

        xmlLocation: null,
        xmlCanvas: null,
        xmlSpotlight: null,
        xmlColors: null,

        xslLocation: null,
        xslHome: null,
        xslSpotlight: null,

        pageSize: 9,
        timeRefresh: null
    };
    var homeView = {
        init: function () {
            octopus.getLocations();
            octopus.getSpotlight();

            // VALIDATE --------------------------------------------------------
            var form = document.getElementById('form');
            form.addEventListener('submit', function (evt) {
                evt.preventDefault();

                // validate
                var validate = validateForm();
                if (validate == false) {
                    return;
                }

                // loading
                var divForm = document.getElementById("div-form");
                var divLoad = document.getElementById("div-loading");
                divForm.style.display = 'none';
                divLoad.style.display = "block";

                // submit form
                var data = new FormData(form);
                octopus.submitForm(data);
            });

            // RADIO TYPE ------------------------------------------------------
            var spanTypeImage = document.getElementById("span-type-image");
            var spanTypeColor = document.getElementById("span-type-color");
            var previewImage = document.getElementById('previewImage');
            var previewColor = document.getElementById('previewColor');
            var divClose = document.getElementById('closePreview');
            // init
            spanTypeImage.style.display = 'block';

            // show input image + hide all preview/close button
            var lbTypeImage = document.getElementById("lbTypeImage");
            lbTypeImage.addEventListener('click', function () {

                if (!document.getElementById('type1Image').checked) {
                    spanTypeImage.style.display = 'block';
                    spanTypeColor.style.display = 'none';

                    divClose.style.display = 'none';
                    previewImage.style.display = 'none';
                    previewColor.style.display = 'none';

                    var palette = document.getElementById('palette');
                    palette.style.display = 'none';
                }
            });
            // show input color + hide all preview/close button
            var lbTypeColor = document.getElementById("lbTypeColor");
            lbTypeColor.addEventListener('click', function () {
                if (!document.getElementById('type1Color').checked) {
                    spanTypeImage.style.display = 'none';
                    spanTypeColor.style.display = 'block';

                    divClose.style.display = 'none';
                    previewImage.style.display = 'none';
                    previewColor.style.display = 'none';

                    var palette = document.getElementById('palette');
                    palette.style.display = 'none';
                }
            });

            // CLOSE -----------------------------------------------------------
            divClose.addEventListener('click', function () {
                spanTypeImage.style.display = 'block';
                spanTypeColor.style.display = 'none';

                divClose.style.display = 'none';
                previewImage.style.display = 'none';
                previewColor.style.display = 'none';

                var palette = document.getElementById('palette');
                palette.style.display = 'none';
            });

            // COLOR CHOOSER ---------------------------------------------------
            var colors = octopus.getXmlColors();
            var color = colors.getElementsByTagName('color');
            var colorList = Array.prototype.slice.call(color);

            colorList.forEach(function (color) {
                var span = document.createElement("span");
                span.setAttribute("class", "span-color-chooser");
                
                var code = color.getElementsByTagName('code');
                var codeList = Array.prototype.slice.call(code);

                codeList.forEach(function (hex) {
                    var colorRadio = document.createElement("input");
                    colorRadio.setAttribute("type", "radio");
                    colorRadio.setAttribute("id", hex.innerHTML);
                    colorRadio.setAttribute("name", "mColor");
                    colorRadio.setAttribute("value", hex.innerHTML);
                    var colorLabel = document.createElement("label");
                    colorLabel.setAttribute("for", hex.innerHTML);
                    colorLabel.setAttribute("class", "label-color");
                    colorLabel.setAttribute("style", "background-color:" + hex.innerHTML + ";");
                    span.appendChild(colorRadio);
                    span.appendChild(colorLabel);
                });
                spanTypeColor.appendChild(span);
            });
        },
        renderLocation: function () {
            if (document.implementation && document.implementation.createDocument)
            {
                xsltProcessor = new XSLTProcessor();
                xsltProcessor.importStylesheet(octopus.getXslLocation());
                resultDocument = xsltProcessor.transformToFragment(octopus.getXmlLocation(), document);
                console.log(resultDocument);

                var myNode = document.getElementById("test");
                while (myNode.firstChild) {
                    myNode.removeChild(myNode.firstChild);
                }
                myNode.appendChild(resultDocument);
            }
        },
        renderSpotlight: function () {
            if (document.implementation && document.implementation.createDocument)
            {
                xsltProcessor = new XSLTProcessor();
                xsltProcessor.importStylesheet(octopus.getXslSpotlight());
                resultDocument = xsltProcessor.transformToFragment(octopus.getXmlSpotlight(), document);
                console.log(resultDocument);

                var myNode = document.getElementById("spotlight");
                while (myNode.firstChild) {
                    myNode.removeChild(myNode.firstChild);
                }
                myNode.appendChild(resultDocument);
                console.log('done append child');
                initSpotlight();
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
            cP.innerHTML = "Tất cả (" + octopus.getTotalResult() + ")";

            cLabel.addEventListener('click', function () {
                homeView.renderPage('0', octopus.getTotalResult());
                homeView.renderCanvasResult('0', 0);
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
                var count = category.getElementsByTagName("count")[0].textContent;

                if (count == 0) {
                    // skip this category
                    return;
                }

                var cDiv = document.createElement("div");
                cDiv.setAttribute("class", "div-category");

                var cRadio = document.createElement("input");
                cRadio.setAttribute("type", "radio");
                cRadio.setAttribute("id", "c" + id);
                cRadio.setAttribute("name", "rbCategory");

                var cLabel = document.createElement("label");
                cLabel.setAttribute("for", "c" + id);

                var cP = document.createElement("p");
                cP.innerHTML = name + ' (' + count + ')';

                cLabel.addEventListener('click', function () {
                    homeView.renderPage(id, count);
                    homeView.renderCanvasResult(id, 0);
                });

                cLabel.appendChild(cP);
                cDiv.appendChild(cRadio);
                cDiv.appendChild(cLabel);
                divCategoryWrap.appendChild(cDiv);

            });
        },
        renderPage: function (categoryId, count) {
            var pageCount = Math.ceil(count / octopus.getPageSize());

            var pageDiv = document.getElementById("div-page-wrap");
            while (pageDiv.firstChild) {
                pageDiv.removeChild(pageDiv.firstChild);
            }

            for (var i = 0; i < pageCount; i++) {

                (function (i) {

                    var pDiv = document.createElement("div");
                    pDiv.setAttribute("class", "div-page");

                    var pRadio = document.createElement("input");
                    pRadio.setAttribute("type", "radio");
                    pRadio.setAttribute("id", "p" + i);
                    pRadio.setAttribute("name", "rbPage");

                    if (i == 0) {
                        pRadio.setAttribute("checked", "checked");
                    }

                    var pLabel = document.createElement("label");
                    pLabel.setAttribute("for", "p" + i);
                    pLabel.setAttribute("class", "label-page");

                    pLabel.innerHTML = i + 1;

//                    var pP = document.createElement("p");
//                    pP.innerHTML = "Page " + (i + 1);
//                    pP.innerHTML = i + 1;

                    pLabel.addEventListener('click', function () {
                        homeView.renderCanvasResult(categoryId, i);
                    });

//                    pLabel.appendChild(pP);
                    pDiv.appendChild(pRadio);
                    pDiv.appendChild(pLabel);
                    pageDiv.appendChild(pDiv);
                }).call(this, i);
            }
        },
        renderCanvasResult: function (categoryId, currentPage) {
            console.log('render canvas');
            if (document.implementation && document.implementation.createDocument)
            {
                xsltProcessor = new XSLTProcessor();
                xsltProcessor.importStylesheet(octopus.getXslHome());
                xsltProcessor.setParameter(null, "categoryId", categoryId + '');
                xsltProcessor.setParameter(null, "currentPage", currentPage);
                xsltProcessor.setParameter(null, "pageSize", octopus.getPageSize());
                resultDocument = xsltProcessor.transformToFragment(octopus.getXmlCanvas(), document);

                document.getElementById("result").innerHTML = '';
                document.getElementById("result").appendChild(resultDocument);

                var divForm = document.getElementById("div-form");
                var divLoad = document.getElementById("div-loading");
                divForm.style.display = 'block';
                divLoad.style.display = "none";
            }
        }
    };

    var octopus = {
        init: function () {
            octopus.getXsl();
            homeView.init();
            octopus.initResfreshSpotlight();
        },
        initResfreshSpotlight: function () {
            console.log('init refresh');
            homeModel.timeRefresh = setInterval(function () {
                console.log('refresh spotlight');
                octopus.getSpotlight();
            }, 1000 * 60 * 6);
        },
        getXsl: function () {
            var url = null;

            url = "/ZCanvas/document/home-location.xsl";
            homeModel.xslLocation = loadXML(url);

            url = "/ZCanvas/document/home.xsl";
            homeModel.xslHome = loadXML(url);

            url = "/ZCanvas/document/home-spotlight.xsl";
            homeModel.xslSpotlight = loadXML(url);

            url = "/ZCanvas/document/colors.xml";
            homeModel.xmlColors = loadXML(url);
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
        getSpotlight: function () {
            var url = "ProcessServlet";
            var param = {
                btAction: 'spotlight'
            };
            sendGetRequest(url, param, function (response) {
                var xmlDoc = response.responseXML;
                homeModel.xmlSpotlight = xmlDoc;
                // render spotlight
                console.log('render spotlight');
                console.log(xmlDoc);
                homeView.renderSpotlight();
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
                homeView.renderPage(0, octopus.getTotalResult());
                homeView.renderCanvasResult(0, 0);
            });
        },
        getXslLocation: function () {
            return homeModel.xslLocation;
        },
        getXslHome: function () {
            return homeModel.xslHome;
        },
        getXslSpotlight: function () {
            return homeModel.xslSpotlight;
        },
        getXmlLocation: function () {
            return homeModel.xmlLocation;
        },
        getXmlCanvas: function () {
            return homeModel.xmlCanvas;
        },
        getXmlColors: function () {
            return homeModel.xmlColors;
        },
        getXmlSpotlight: function () {
            return homeModel.xmlSpotlight;
        },
        getListColors: function () {
            return homeModel.listInputColors;
        },
        getListCategories: function () {
            return homeModel.listCategories;
        },
        getTotalResult: function () {
            return homeModel.totalResult;
        },
        getPageSize: function () {
            return homeModel.pageSize;
        }
    };

    octopus.init();
}());
