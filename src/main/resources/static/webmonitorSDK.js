/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// 全局id算法
function monitorGuid() {
    function S4() {
        return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
    }
    return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
}
const monitorId = monitorGuid();

// 各主流浏览器
function getBrowserInfo() {
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
function getOsInfo() {
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
function getMonitorUserInfo() {
    const user = {
        // 用户信息
        userName: 'test',

        // 屏幕宽度
        width: screen.width,
        // 屏幕高度
        height: screen.height,

        // 浏览器型号
        browser: getBrowserInfo(),
        // 浏览器版本
        os: getOsInfo(),

        // 浏览器用户界面的语言
        language: navigator.language,
    }

    return user;
}

// 抓取性能信息
function getPerformanceInfo() {
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
    }

    return performance;
}

// 信息对象
function monitorInit() {
    const currentTime = new Date().getTime();
    const monitor = {
        // 全局ID
        guid: monitorId,
        // 用户时间戳
        time: currentTime,
        // 入口页面路径
        url: window.location.href,
        // 性能信息
        performance: getPerformanceInfo(),
        // 用户信息
        user: getMonitorUserInfo(),
    }

    return monitor;
}

// 在浏览器空闲时间,获取监控信息和发送请求
window.onload = () => {
    const monitor = monitorInit();

    if (window.requestIdleCallback) {
        window.requestIdleCallback(() => {
            sendInfo2Server('/info', monitor);
        })
    } else {
        setTimeout(() => {
            sendInfo2Server('/info', monitor);
        }, 0)
    }
}

//beacon发送
function sendBeaconRequest(url, params) {
    const blob = new Blob([JSON.stringify(params)]);
    navigator.sendBeacon(url, blob);
}

// 像素发送
function sendPxPointRequest(url, params) {
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

// 发送Trace
function sendInfo2Server(url, params) {
    // 数据上传地址
    const fullUrl = monitorDomain + url;
    if (navigator.sendBeacon) {
        sendBeaconRequest(fullUrl, params);
    } else {
        sendPxPointRequest(fullUrl, params);
    }
}

// 监听js错误
window.onerror = function (msg, url, row, col, error) {
    const errorInfo = {
        // 全局ID
        guid: monitorId,
        type: 'javascript', // 错误类型
        rowNum: row, // 发生错误时的代码行数
        colNum: col, // 发生错误时的代码列数
        msg: error && error.stack ? error.stack : msg, // 错误信息
        url: url, // 错误文件
        time: new Date().getTime(), // 错误发生的时间
    }

    sendInfo2Server('/error', errorInfo);
}

// 发送进入页面动作
const enterPage = {
    // 全局ID
    guid: monitorId,
    type: "ENTER_PAGE",
    url: window.location.href,
    module: 'page',
    time: new Date().getTime(),
}
sendInfo2Server('/event', enterPage);

// 发送切屏动作
const changePageHandle = () => {
    if (document.visibilityState === 'hidden') {
        const changePage = {
            // 全局ID
            guid: monitorId,
            type: "CHANGE_PAGE",
            url: window.location.href,
            module: 'page',
            time: new Date().getTime(),
        }
        sendInfo2Server('/event', changePage);
    } else {
        const changePage = {
            // 全局ID
            guid: monitorId,
            type: "FOCUSIN_PAGE",
            url: window.location.href,
            module: 'page',
            time: new Date().getTime(),
        }
        sendInfo2Server('/event', changePage);
    }
}
document.addEventListener('visibilitychange', changePageHandle);

// 发送离开页面动作
const unloadHandle = () => {
    const leavePage = {
        // 全局ID
        guid: monitorId,
        type: "LEAVE_PAGE",
        url: window.location.href,
        module: 'page',
        time: new Date().getTime(),
    }
    sendInfo2Server('/event', leavePage);
}
window.addEventListener('beforeunload', unloadHandle);

// 发送业务动作
function sendUserEvent(userType, userModule) {
    const userEvent = {
        // 全局ID
        guid: monitorId,
        type: userType,
        url: window.location.href,
        module: userModule,
        time: new Date().getTime(),
    }
    sendInfo2Server('/event', userEvent);
}

// 发送进入模块动作
function sendUserEventClick(userModule) {
    sendUserEvent('ENTER_MODULE', userModule);
}
// 发送下钻动作
function sendUserEventDrill(userModule) {
    sendUserEvent('DRILL_MODULE', userModule);
}
// 发送退出模块动作
function sendUserEventQuit(userModule) {
    sendUserEvent('QUIT_MODULE', userModule);
}