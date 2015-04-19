function html(){
  var $intern_14 = '', $intern_11 = ' top: -1000px;', $intern_37 = '" for "gwt:onLoadErrorFn"', $intern_35 = '" for "gwt:onPropertyErrorFn"', $intern_20 = '");', $intern_38 = '#', $intern_51 = '.cache.js', $intern_40 = '/', $intern_46 = '//', $intern_50 = ':', $intern_29 = '::', $intern_63 = ':moduleBase', $intern_13 = '<!doctype html>', $intern_15 = '<html><head><\/head><body><\/body><\/html>', $intern_32 = '=', $intern_39 = '?', $intern_34 = 'Bad handler "', $intern_49 = 'C19EDA1A46A94E2BB14AFE4B3C39566B', $intern_12 = 'CSS1Compat', $intern_18 = 'Chrome', $intern_17 = 'DOMContentLoaded', $intern_6 = 'DUMMY', $intern_62 = 'Ignoring non-whitelisted Dev Mode URL: ', $intern_61 = '__gwtDevModeHook:html', $intern_45 = 'base', $intern_43 = 'baseUrl', $intern_1 = 'begin', $intern_7 = 'body', $intern_0 = 'bootstrap', $intern_42 = 'clear.cache.gif', $intern_31 = 'content', $intern_58 = 'end', $intern_19 = 'eval("', $intern_60 = 'file:', $intern_2 = 'gwt.codesvr.html=', $intern_3 = 'gwt.codesvr=', $intern_57 = 'gwt/chrome/chrome.css', $intern_36 = 'gwt:onLoadErrorFn', $intern_33 = 'gwt:onPropertyErrorFn', $intern_30 = 'gwt:property', $intern_25 = 'head', $intern_55 = 'href', $intern_4 = 'html', $intern_48 = 'html.devmode.js', $intern_44 = 'html.nocache.js', $intern_28 = 'html::', $intern_59 = 'http:', $intern_8 = 'iframe', $intern_41 = 'img', $intern_22 = 'javascript', $intern_9 = 'javascript:""', $intern_52 = 'link', $intern_56 = 'loadExternalRefs', $intern_26 = 'meta', $intern_24 = 'moduleRequested', $intern_23 = 'moduleStartup', $intern_27 = 'name', $intern_10 = 'position:absolute; width:0; height:0; border:none; left: -1000px;', $intern_53 = 'rel', $intern_21 = 'script', $intern_47 = 'selectingPermutation', $intern_5 = 'startup', $intern_54 = 'stylesheet', $intern_16 = 'undefined';
  var $wnd = window;
  var $doc = document;
  sendStats($intern_0, $intern_1);
  function isHostedMode(){
    var query = $wnd.location.search;
    return query.indexOf($intern_2) != -1 || query.indexOf($intern_3) != -1;
  }

  function sendStats(evtGroupString, typeString){
    if ($wnd.__gwtStatsEvent) {
      $wnd.__gwtStatsEvent({moduleName:$intern_4, sessionId:$wnd.__gwtStatsSessionId, subSystem:$intern_5, evtGroup:evtGroupString, millis:(new Date).getTime(), type:typeString});
    }
  }

  html.__sendStats = sendStats;
  html.__moduleName = $intern_4;
  html.__errFn = null;
  html.__moduleBase = $intern_6;
  html.__softPermutationId = 0;
  html.__computePropValue = null;
  html.__getPropMap = null;
  html.__gwtInstallCode = function(){
  }
  ;
  html.__gwtStartLoadingFragment = function(){
    return null;
  }
  ;
  var __gwt_isKnownPropertyValue = function(){
    return false;
  }
  ;
  var __gwt_getMetaProperty = function(){
    return null;
  }
  ;
  __propertyErrorFunction = null;
  var activeModules = $wnd.__gwt_activeModules = $wnd.__gwt_activeModules || {};
  activeModules[$intern_4] = {moduleName:$intern_4};
  var frameDoc;
  function getInstallLocationDoc(){
    setupInstallLocation();
    return frameDoc;
  }

  function getInstallLocation(){
    setupInstallLocation();
    return frameDoc.getElementsByTagName($intern_7)[0];
  }

  function setupInstallLocation(){
    if (frameDoc) {
      return;
    }
    var scriptFrame = $doc.createElement($intern_8);
    scriptFrame.src = $intern_9;
    scriptFrame.id = $intern_4;
    scriptFrame.style.cssText = $intern_10 + $intern_11;
    scriptFrame.tabIndex = -1;
    $doc.body.appendChild(scriptFrame);
    frameDoc = scriptFrame.contentDocument;
    if (!frameDoc) {
      frameDoc = scriptFrame.contentWindow.document;
    }
    frameDoc.open();
    var doctype = document.compatMode == $intern_12?$intern_13:$intern_14;
    frameDoc.write(doctype + $intern_15);
    frameDoc.close();
  }

  function installScript(filename){
    function setupWaitForBodyLoad(callback){
      function isBodyLoaded(){
        if (typeof $doc.readyState == $intern_16) {
          return typeof $doc.body != $intern_16 && $doc.body != null;
        }
        return /loaded|complete/.test($doc.readyState);
      }

      var bodyDone = isBodyLoaded();
      if (bodyDone) {
        callback();
        return;
      }
      function onBodyDone(){
        if (!bodyDone) {
          bodyDone = true;
          callback();
          if ($doc.removeEventListener) {
            $doc.removeEventListener($intern_17, onBodyDone, false);
          }
          if (onBodyDoneTimerId) {
            clearInterval(onBodyDoneTimerId);
          }
        }
      }

      if ($doc.addEventListener) {
        $doc.addEventListener($intern_17, onBodyDone, false);
      }
      var onBodyDoneTimerId = setInterval(function(){
        if (isBodyLoaded()) {
          onBodyDone();
        }
      }
      , 50);
    }

    function installCode(code_0){
      function removeScript(body_0, element){
      }

      var docbody = getInstallLocation();
      var doc = getInstallLocationDoc();
      var script;
      if (navigator.userAgent.indexOf($intern_18) > -1 && window.JSON) {
        var scriptFrag = doc.createDocumentFragment();
        scriptFrag.appendChild(doc.createTextNode($intern_19));
        for (var i = 0; i < code_0.length; i++) {
          var c = window.JSON.stringify(code_0[i]);
          scriptFrag.appendChild(doc.createTextNode(c.substring(1, c.length - 1)));
        }
        scriptFrag.appendChild(doc.createTextNode($intern_20));
        script = doc.createElement($intern_21);
        script.language = $intern_22;
        script.appendChild(scriptFrag);
        docbody.appendChild(script);
        removeScript(docbody, script);
      }
       else {
        for (var i = 0; i < code_0.length; i++) {
          script = doc.createElement($intern_21);
          script.language = $intern_22;
          script.text = code_0[i];
          docbody.appendChild(script);
          removeScript(docbody, script);
        }
      }
    }

    html.onScriptDownloaded = function(code_0){
      setupWaitForBodyLoad(function(){
        installCode(code_0);
      }
      );
    }
    ;
    sendStats($intern_23, $intern_24);
    var script = $doc.createElement($intern_21);
    script.src = filename;
    $doc.getElementsByTagName($intern_25)[0].appendChild(script);
  }

  html.__startLoadingFragment = function(fragmentFile){
    return computeUrlForResource(fragmentFile);
  }
  ;
  html.__installRunAsyncCode = function(code_0){
    var docbody = getInstallLocation();
    var script = getInstallLocationDoc().createElement($intern_21);
    script.language = $intern_22;
    script.text = code_0;
    docbody.appendChild(script);
  }
  ;
  function processMetas(){
    var metaProps = {};
    var propertyErrorFunc;
    var onLoadErrorFunc;
    var metas = $doc.getElementsByTagName($intern_26);
    for (var i = 0, n = metas.length; i < n; ++i) {
      var meta = metas[i], name_0 = meta.getAttribute($intern_27), content;
      if (name_0) {
        name_0 = name_0.replace($intern_28, $intern_14);
        if (name_0.indexOf($intern_29) >= 0) {
          continue;
        }
        if (name_0 == $intern_30) {
          content = meta.getAttribute($intern_31);
          if (content) {
            var value_0, eq = content.indexOf($intern_32);
            if (eq >= 0) {
              name_0 = content.substring(0, eq);
              value_0 = content.substring(eq + 1);
            }
             else {
              name_0 = content;
              value_0 = $intern_14;
            }
            metaProps[name_0] = value_0;
          }
        }
         else if (name_0 == $intern_33) {
          content = meta.getAttribute($intern_31);
          if (content) {
            try {
              propertyErrorFunc = eval(content);
            }
             catch (e) {
              alert($intern_34 + content + $intern_35);
            }
          }
        }
         else if (name_0 == $intern_36) {
          content = meta.getAttribute($intern_31);
          if (content) {
            try {
              onLoadErrorFunc = eval(content);
            }
             catch (e) {
              alert($intern_34 + content + $intern_37);
            }
          }
        }
      }
    }
    __gwt_getMetaProperty = function(name_0){
      var value_0 = metaProps[name_0];
      return value_0 == null?null:value_0;
    }
    ;
    __propertyErrorFunction = propertyErrorFunc;
    html.__errFn = onLoadErrorFunc;
  }

  function computeScriptBase(){
    function getDirectoryOfFile(path){
      var hashIndex = path.lastIndexOf($intern_38);
      if (hashIndex == -1) {
        hashIndex = path.length;
      }
      var queryIndex = path.indexOf($intern_39);
      if (queryIndex == -1) {
        queryIndex = path.length;
      }
      var slashIndex = path.lastIndexOf($intern_40, Math.min(queryIndex, hashIndex));
      return slashIndex >= 0?path.substring(0, slashIndex + 1):$intern_14;
    }

    function ensureAbsoluteUrl(url_0){
      if (url_0.match(/^\w+:\/\//)) {
      }
       else {
        var img = $doc.createElement($intern_41);
        img.src = url_0 + $intern_42;
        url_0 = getDirectoryOfFile(img.src);
      }
      return url_0;
    }

    function tryMetaTag(){
      var metaVal = __gwt_getMetaProperty($intern_43);
      if (metaVal != null) {
        return metaVal;
      }
      return $intern_14;
    }

    function tryNocacheJsTag(){
      var scriptTags = $doc.getElementsByTagName($intern_21);
      for (var i = 0; i < scriptTags.length; ++i) {
        if (scriptTags[i].src.indexOf($intern_44) != -1) {
          return getDirectoryOfFile(scriptTags[i].src);
        }
      }
      return $intern_14;
    }

    function tryBaseTag(){
      var baseElements = $doc.getElementsByTagName($intern_45);
      if (baseElements.length > 0) {
        return baseElements[baseElements.length - 1].href;
      }
      return $intern_14;
    }

    function isLocationOk(){
      var loc = $doc.location;
      return loc.href == loc.protocol + $intern_46 + loc.host + loc.pathname + loc.search + loc.hash;
    }

    var tempBase = tryMetaTag();
    if (tempBase == $intern_14) {
      tempBase = tryNocacheJsTag();
    }
    if (tempBase == $intern_14) {
      tempBase = tryBaseTag();
    }
    if (tempBase == $intern_14 && isLocationOk()) {
      tempBase = getDirectoryOfFile($doc.location.href);
    }
    tempBase = ensureAbsoluteUrl(tempBase);
    return tempBase;
  }

  function computeUrlForResource(resource){
    if (resource.match(/^\//)) {
      return resource;
    }
    if (resource.match(/^[a-zA-Z]+:\/\//)) {
      return resource;
    }
    return html.__moduleBase + resource;
  }

  function getCompiledCodeFilename(){
    var answers = [];
    var softPermutationId;
    var values = [];
    var providers = [];
    function computePropValue(propName){
      var value_0 = providers[propName](), allowedValuesMap = values[propName];
      if (value_0 in allowedValuesMap) {
        return value_0;
      }
      var allowedValuesList = [];
      for (var k in allowedValuesMap) {
        allowedValuesList[allowedValuesMap[k]] = k;
      }
      if (__propertyErrorFunc) {
        __propertyErrorFunc(propName, allowedValuesList, value_0);
      }
      throw null;
    }

    __gwt_isKnownPropertyValue = function(propName, propValue){
      return propValue in values[propName];
    }
    ;
    html.__getPropMap = function(){
      var result = {};
      for (var key in values) {
        if (values.hasOwnProperty(key)) {
          result[key] = computePropValue(key);
        }
      }
      return result;
    }
    ;
    html.__computePropValue = computePropValue;
    $wnd.__gwt_activeModules[$intern_4].bindings = html.__getPropMap;
    sendStats($intern_0, $intern_47);
    if (isHostedMode()) {
      return computeUrlForResource($intern_48);
    }
    var strongName;
    try {
      strongName = $intern_49;
      var idx = strongName.indexOf($intern_50);
      if (idx != -1) {
        softPermutationId = parseInt(strongName.substring(idx + 1), 10);
        strongName = strongName.substring(0, idx);
      }
    }
     catch (e) {
    }
    html.__softPermutationId = softPermutationId;
    return computeUrlForResource(strongName + $intern_51);
  }

  function loadExternalStylesheets(){
    if (!$wnd.__gwt_stylesLoaded) {
      $wnd.__gwt_stylesLoaded = {};
    }
    function installOneStylesheet(stylesheetUrl){
      if (!__gwt_stylesLoaded[stylesheetUrl]) {
        var l = $doc.createElement($intern_52);
        l.setAttribute($intern_53, $intern_54);
        l.setAttribute($intern_55, computeUrlForResource(stylesheetUrl));
        $doc.getElementsByTagName($intern_25)[0].appendChild(l);
        __gwt_stylesLoaded[stylesheetUrl] = true;
      }
    }

    sendStats($intern_56, $intern_1);
    installOneStylesheet($intern_57);
    sendStats($intern_56, $intern_58);
  }

  processMetas();
  html.__moduleBase = computeScriptBase();
  activeModules[$intern_4].moduleBase = html.__moduleBase;
  var filename = getCompiledCodeFilename();
  if ($wnd) {
    var devModePermitted = !!($wnd.location.protocol == $intern_59 || $wnd.location.protocol == $intern_60);
    $wnd.__gwt_activeModules[$intern_4].canRedirect = devModePermitted;
    if (devModePermitted) {
      var devModeKey = $intern_61;
      var devModeUrl = $wnd.sessionStorage[devModeKey];
      if (!/^http:\/\/(localhost|127\.0\.0\.1)(:\d+)?\/.*$/.test(devModeUrl)) {
        if (devModeUrl && (window.console && console.log)) {
          console.log($intern_62 + devModeUrl);
        }
        devModeUrl = $intern_14;
      }
      if (devModeUrl && !$wnd[devModeKey]) {
        $wnd[devModeKey] = true;
        $wnd[devModeKey + $intern_63] = computeScriptBase();
        var devModeScript = $doc.createElement($intern_21);
        devModeScript.src = devModeUrl;
        var head = $doc.getElementsByTagName($intern_25)[0];
        head.insertBefore(devModeScript, head.firstElementChild || head.children[0]);
        return false;
      }
    }
  }
  loadExternalStylesheets();
  sendStats($intern_0, $intern_58);
  installScript(filename);
  return true;
}

html.succeeded = html();
