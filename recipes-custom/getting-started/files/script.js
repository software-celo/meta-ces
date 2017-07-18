(function() {
    var userAgent = window.navigator.userAgent;
    var screenWidth = window.screen.width;
    var screenHeight = window.screen.height;
    var screenColorDeep = window.screen.colorDepth;

    document.getElementById('userAgentContainer').innerHTML = userAgent;
    document.getElementById('screenWidthContainer').innerHTML = screenWidth;
    document.getElementById('screenHeightContainer').innerHTML = screenHeight;
    document.getElementById('screenColorDeepContainer').innerHTML = screenColorDeep;
})();