// 3. On Reveal.js ready event, copy header/footer <div> into each `.slide-background` <div>


// window.addEventListener('load', hogwarts_load)
// window.onload = hogwarts_load;

const HogwartsPPTPlugin = () => {

    function show_logo() {
        var logo_default = `
    <div class="hogwarts logo">   
        <img src="${scriptPath()}hogwarts.png" >
        <p>霍格沃兹测试开发<br>ceshiren.com</p>
    </div>
    `

        // var header = document.querySelector('#header').innerHTML;
        if (window.location.search.match(/print-pdf/gi)) {
            Reveal.addEventListener('ready', function (event) {
                document.querySelector('.slide-background').append(header);
            });
        }
        else {
            let logo = document.querySelector('.hogwarts.logo')
            let node = document.createElement("div");
            if (logo) {
                node=logo
            } else {
                node.innerHTML = logo_default;
            }

            document.querySelector('div.reveal').appendChild(node);
        }
    }

    function hogwarts_layout() {
        var titles = ['H1', 'H2', 'H3']

        var sections = document.querySelectorAll(".reveal section")
        sections.forEach(section => {
            //每一页ppt
            var title = document.createElement('div')
            var split_line = document.createElement('div')
            split_line.classList.add('hogwarts', 'line')
            var content = document.createElement('div')
            content.classList.add('hogwarts', 'content')
            var has_title = false

            var children = section.querySelectorAll(':scope > *');
            children.forEach(child => {
                if (titles.includes(child.tagName)) {
                    title.appendChild(child)

                    if (child.tagName === 'H1') {
                        title.classList.add('hogwarts', 'big_title')
                    } else {
                        title.classList.add('hogwarts', 'title')
                    }
                    has_title = true
                } else if (child.getAttribute('class') === 'slide-background-content') {
                    console.log('hidden');
                } else {
                    content.appendChild(child)
                }

            })

            title.appendChild(split_line)

            section.prepend(content)
            if (has_title) {
                section.prepend(title)
            } else {
                content.classList.add('hogwarts', 'fill')
            }

            if (content.hasChildNodes() === false) {
                title.classList.add('hogwarts', 'only')
            }

        })

        // 如果p是容器的话，设置属性方便css去套样式
        var p_list = document.querySelectorAll(".reveal section p")
        p_list.forEach(p => {
            let img = p.querySelector(':scope > img')
            if (img) {
                p.classList.add('hogwarts', 'container')
            }
            if (p.textContent !== '') {
                p.classList.add('hogwarts', 'text')
            }
        })


    }

    function layout_big() {
        // 内部有图的content适当的放大，加个标记通过css实现
        document.querySelectorAll(".reveal .present .hogwarts.content > *").forEach(item => {
            if (item.offsetHeight > item.parentElement.offsetHeight / 2) {
                item.parentElement.classList.add('hogwarts', 'big')
            }
        })
    }

    function resize_element(domText) {
        let domContainer = domText.parentElement;
        let itemWidth = domText.offsetWidth - 80
        let containerWidth = domContainer.offsetWidth - 80
        let containerHeight = domContainer.offsetHeight
        let r = 1

        if (itemWidth > containerWidth) {
            r = containerWidth / itemWidth;
        }
        if (domText.offsetHeight > containerHeight) {
            r = containerHeight / domText.offsetHeight;
        }

        // if (domText.scrollHeight > domText.clientHeight) {
        //     r = domText.clientHeight / (domText.scrollHeight + 40)
        // }

        if (r !== 1) {
            console.log(r);
            domText.style.transform = 'scale(' + r + ')';
            // domText.style.flexFlow='flex-start'
            if (domText.tagName === "TABLE") {
                domText.style.transformOrigin = "top";
            } else {
                domText.style.transformOrigin = "center";
            }
            if (domText.tagName === 'P') {
                domText.style.alignSelf = 'baseline'
            }

        }
    }

    function resize_slide() {
        // var text_tags = ['UL', 'P', 'TABLE', 'CODE', '.container.text']
        var text_tags = ['.content', '.row', '.column', '.container']
        text_tags.forEach(tag => {
            var children = document.querySelectorAll(`.present .hogwarts${tag.toLowerCase()} > *`);
            children.forEach(child => {
                resize_element(child)
            })
        })
    }

    // 为了打印pdf
    function resize_img() {
        document.querySelectorAll('.present img').forEach(img => {
            img.style.height = img.offsetHeight + 'px'
            img.style.width = img.offsetWidth + 'px'
        })
    }

    function handle_plantuml() {
        document.querySelectorAll('.reveal pre.plantuml code').forEach(block => {
            block.classList.add('language-plantuml')
        })
    }

    function mobile_layout() {
        if (navigator.userAgent.indexOf('Android') > -1 || navigator.userAgent.indexOf('iPhone') > -1) {
            console.log('mobile 切换横屏模式');
            let height = document.body.clientHeight
            let width = document.body.clientWidth
            document.body.style.width = height + 'px'
            document.body.style.height = width + 'px'
            document.body.style.top = (height - width) / 2 + 'px'
            document.body.style.left = 0 - (height - width) / 2 + 'px'

            document.body.style.transform = 'rotate(90deg)'
        }
    }

    function lazy_load_image() {
        document.querySelectorAll('.reveal .hogwarts.content img').forEach(img => {
            // let src=img.getAttribute('src')
            // img.setAttribute('data-src', src)
            // img.removeAttribute('src')
            img.setAttribute('loading', 'lazy')
        })
    }

    function scriptPath() {
        // obtain plugin path from the script element
        var src;
        if (document.currentScript) {
            src = document.currentScript.src;
        } else {
            var sel = document.querySelector('script[src$="/hogwarts/plugin.js"]')
            if (sel) {
                src = sel.src;
            }
        }

        var path = typeof src === undefined ? src
            : src.slice(0, src.lastIndexOf("/") + 1);
        return path;
    }

    // modified from math plugin
    function loadResource(url, type, callback) {
        var head = document.querySelector('head');
        var resource;

        if (type === 'script') {
            resource = document.createElement('script');
            resource.type = 'text/javascript';
            resource.src = url;
        } else if (type === 'stylesheet') {
            resource = document.createElement('link');
            resource.rel = 'stylesheet';
            resource.href = url;
        }

        // Wrapper for callback to make sure it only fires once
        var finish = function () {
            if (typeof callback === 'function') {
                callback.call();
                callback = null;
            }
        };

        resource.onload = finish;

        // IE
        resource.onreadystatechange = function () {
            if (this.readyState === 'loaded') {
                finish();
            }
        };

        // Normal browsers
        head.appendChild(resource);
    }

    function hogwarts_load() {
        console.log("hogwarts ppt plugin load");

        Reveal.on('ready', event => {
            // event.currentSlide, event.indexh, event.indexv
            //todo: onload的时候会错过ready
            console.log('ready')
            if (document.location.search.includes('debug')){
                console.log('debug model, will not use hogwarts.css, you should add yours in the ppt')
            }else{
                loadResource(scriptPath() + '/hogwarts.css', 'stylesheet', null)
            }
            
            loadResource(scriptPath() + '../semantic/semantic.js', 'stylesheet', null)
            loadResource(scriptPath() + '../semantic/semantic.css', 'stylesheet', null)
            console.log("show_logo")
            show_logo();
            console.log("lazy_load_image")
            lazy_load_image()
            console.log("hogwarts_layout")
            hogwarts_layout();
            console.log("layout_big")
            layout_big()
            console.log("mobile_layout")
            mobile_layout()

            console.log("resize_slide")
            resize_slide()
            console.log("handle_plantuml")
            handle_plantuml()
            // hljs.highlightAll()
            console.log('hogwarts ppt ready');
            // console.log(event);
        });

        Reveal.on('slidechanged', event => {
            // console.log(event);
            // event.previousSlide, event.currentSlide, event.indexh, event.indexv
            console.log("slidechanged")
            layout_big()
            resize_slide()
            // 如果需要打印pdf，请打开此选项
            if (window.location.search === '?pdf') {
                console.log('pdf format');
                resize_img()
            }


        });

        window.addEventListener('load', function () {
            console.log("onload")
            resize_slide()
        })
    }

    return {
        id: 'hogwarts_ppt',
        init: function (reveal) {
            hogwarts_load()
        }
    }
}

// export default Plugin
window.RevealHogwartsPPT = window.RevealHogwartsPPT || HogwartsPPTPlugin




