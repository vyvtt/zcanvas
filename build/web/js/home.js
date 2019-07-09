(function () {

    var homeModel = {
        listInputColors: [],
        listCategories: [],
        totalResult: null,

        xmlLocation: null,
        xmlCanvas: null,

        xslLocation: null,
        xslHome: null,

        pageSize: 9
    };
    var homeView = {
        init: function () {
            octopus.getLocations();

            // VALIDATE -----------
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
            
            // RADIO TYPE -----------
            var spanTypeImage = document.getElementById("span-type-image");
            var spanTypeColor = document.getElementById("span-type-color");
            
            var lbTypeImage = document.getElementById("lbTypeImage");
            lbTypeImage.addEventListener('click', function () {
                spanTypeImage.style.display = 'block';
                spanTypeColor.style.display = 'none';
            });
            
            var lbTypeColor = document.getElementById("lbTypeColor");
            lbTypeColor.addEventListener('click', function () {
                spanTypeImage.style.display = 'none';
                spanTypeColor.style.display = 'block';
            });
            
        },
        renderLocation: function () {
            if (document.implementation && document.implementation.createDocument)
            {
//                var scripts = document.getElementsByTagName("script");
//                var src = scripts[scripts.length - 1].src;
//                console.log(src);
//                
//                xslUrl = "/ZCanvas/document/categories.xml";
//                var category = loadXML(xslUrl);
//                console.log('load test xml');
//                console.log(category);
//                var parser = new DOMParser();
//                var xmlDoc = parser.parseFromString('<categories><category><id>1</id><name>BST Sài Gòn</name></category><category><id>2</id><name>Quotes, typography</name></category><category><id>3</id><name>Ẩm thực</name></category><category><id>4</id><name>BTS Cao Minh Huy</name></category><category><id>5</id><name>BTS Mix and Match</name></category><category><id>6</id><name>Cuộc sống</name></category><category><id>7</id><name>Thiên nhiên</name></category><category><id>8</id><name>BTS Lê Rin</name></category><category><id>9</id><name>Động vật</name></category><category><id>10</id><name>Thành phố, landscape</name></category><category><id>11</id><name>Trừu tượng, nghệ thuật, hội họa</name></category><category><id>12</id><name>BTS Đốm Illustration</name></category><category><id>13</id><name>Tranh treo cửa hàng</name></category><category><id>14</id><name>Xe Cộ</name></category><category><id>15</id><name>Tranh treo văn phòng</name></category><category><id>16</id><name>Văn Hóa</name></category><category><id>17</id><name>Digital Art</name></category><category><id>18</id><name>Phim Ảnh</name></category><category><id>19</id><name>Bản Đồ</name></category><category><id>20</id><name>Lowpoly</name></category><category><id>21</id><name>Giáo Dục</name></category><category><id>22</id><name>Âm Nhạc</name></category></categories>', "application/xml");

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
