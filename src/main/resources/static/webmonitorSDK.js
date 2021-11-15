/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// 各主流浏览器
function getBrowser() {
    var u = navigator.userAgent;

    var bws = [{
            name: 'sgssapp',
            it: /sogousearch/i.test(u)
        }, {
            name: 'wechat',
            it: /MicroMessenger/i.test(u)
        }, {
            name: 'weibo',
            it: !!u.match(/Weibo/i)
        }, {
            name: 'uc',
            it: !!u.match(/UCBrowser/i) || u.indexOf(' UBrowser') > -1
        }, {
            name: 'sogou',
            it: u.indexOf('MetaSr') > -1 || u.indexOf('Sogou') > -1
        }, {
            name: 'xiaomi',
            it: u.indexOf('MiuiBrowser') > -1
        }, {
            name: 'baidu',
            it: u.indexOf('Baidu') > -1 || u.indexOf('BIDUBrowser') > -1
        }, {
            name: '360',
            it: u.indexOf('360EE') > -1 || u.indexOf('360SE') > -1
        }, {
            name: '2345',
            it: u.indexOf('2345Explorer') > -1
        }, {
            name: 'edge',
            it: u.indexOf('Edge') > -1
        }, {
            name: 'ie11',
            it: u.indexOf('Trident') > -1 && u.indexOf('rv:11.0') > -1
        }, {
            name: 'ie',
            it: u.indexOf('compatible') > -1 && u.indexOf('MSIE') > -1
        }, {
            name: 'firefox',
            it: u.indexOf('Firefox') > -1
        }, {
            name: 'safari',
            it: u.indexOf('Safari') > -1 && u.indexOf('Chrome') === -1
        }, {
            name: 'qqbrowser',
            it: u.indexOf('MQQBrowser') > -1 && u.indexOf(' QQ') === -1
        }, {
            name: 'qq',
            it: u.indexOf('QQ') > -1
        }, {
            name: 'chrome',
            it: u.indexOf('Chrome') > -1 || u.indexOf('CriOS') > -1
        }, {
            name: 'opera',
            it: u.indexOf('Opera') > -1 || u.indexOf('OPR') > -1
        }];

    for (var i = 0; i < bws.length; i++) {
        if (bws[i].it) {
            return bws[i].name;
        }
    }

    return 'other';
}

// 系统区分
function getOS() {
    var u = navigator.userAgent;
    if (!!u.match(/compatible/i) || u.match(/Windows/i)) {
        return 'windows';
    } else if (!!u.match(/Macintosh/i) || u.match(/MacIntel/i)) {
        return 'macOS';
    } else if (!!u.match(/iphone/i) || u.match(/Ipad/i)) {
        return 'ios';
    } else if (!!u.match(/android/i)) {
        return 'android';
    } else {
        return 'other';
    }
}

// 抓取用户信息
function getUser() {
    const user = {
        // 屏幕宽度
        screen: screen.width,
        // 屏幕高度
        height: screen.height,

        // 浏览器平台
        browser: getBrowser(),
        //浏览器版本
        os: getOS(),

        // 浏览器的用户代理信息
        userAgent: navigator.userAgent,
        // 浏览器用户界面的语言
        language: navigator.language,
    }

    return user;
}

// 抓取性能信息
function getPerformance() {
    if (!window.performance)
        return;
    const timing = window.performance.timing;
    const performance = {
        // 重定向耗时
        redirect: timing.redirectEnd - timing.redirectStart,
        // 白屏时间
        //whiteScreen: nil,
        // DOM 渲染耗时
        dom: timing.domComplete - timing.domLoading,
        // 页面加载耗时
        load: timing.loadEventEnd - timing.navigationStart,
        // 页面卸载耗时
        unload: timing.unloadEventEnd - timing.unloadEventStart,
        // 请求耗时
        request: timing.responseEnd - timing.requestStart,
        // 获取性能信息时当前时间
        time: new Date().getTime(),
    }

    return performance;

}

// 信息对象
function monitorInit() {
    const monitor = {
        // 性能信息
        performance: getPerformance(),
        // 错误信息
        errors: [],
        // 用户信息
        user: getUser(),
    }

    return monitor;
}

// 监控信息
const monitor = monitorInit()

// 在浏览器空闲时间获取性能及资源信息 https://developer.mozilla.org/zh-CN/docs/Web/API/Window/requestIdleCallback
window.onload = () => {
    if (window.requestIdleCallback) {
        window.requestIdleCallback(() => {
            sendMonitor()
        })
    } else {
        setTimeout(() => {
            sendMonitor()
        }, 0)
    }
}

// 数据上传地址
const url = 'http://localhost:8080/api/event';

//beacon发送
function sendBeacon(url, params) {
    const blob = new Blob([JSON.stringify(params)]);
    navigator.sendBeacon(url, blob);
}

// 像素埋点
function sendPxPoint(url, params) {
    const img = new Image();

    img.style.display = 'none';

    const removeImage = function () {
        img.parentNode.removeChild(img);
    }

    img.onload = removeImage;
    img.onerror = removeImage;

    const data = new URLSearchParams(params);
    img.src = `${url}?${data}`;

    document.body.appendChild(img);
}

// 发送日志
function sendLog(params) {
    if (navigator.sendBeacon) {
        sendBeacon(url, params);
    } else {
        sendPxPoint(url, params);
    }
}

// 发送监控信息
function sendMonitor() {
    console.log('send web monitor info');
    console.log(monitor);
    sendLog(monitor);
}