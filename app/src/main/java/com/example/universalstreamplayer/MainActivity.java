package com.example.universalstreamplayer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("acestream://") || url.startsWith("intent://") ||
                    url.startsWith("vlc://") || url.startsWith("mxplayer://") ||
                    url.startsWith("nplayer-") || url.startsWith("infuse://")) {
                    try {
                        android.content.Intent intent = new android.content.Intent(
                            android.content.Intent.ACTION_VIEW, 
                            android.net.Uri.parse(url)
                        );
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

        String htmlContent = getHTMLContent();
        webView.loadDataWithBaseURL("https://example.com", htmlContent, "text/html", "UTF-8", null);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private String getHTMLContent() {
        return "<!DOCTYPE html>\n" +
"<html lang=\"es\">\n" +
"<head>\n" +
"  <meta charset=\"utf-8\" />\n" +
"  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\" />\n" +
"  <title>Reproductor Universal de Streams</title>\n" +
"  <link href=\"https://vjs.zencdn.net/8.23.3/video-js.css\" rel=\"stylesheet\" />\n" +
"  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css\">\n" +
"  <style>\n" +
"    :root{ --primary-color:#00aaff; --primary-hover-color:#0088cc; --background-color:#121212; --surface-color:#1e1e1e; --text-color:#fff; --text-muted-color:#aaa; --border-radius:8px; --font-family:system-ui,-apple-system,BlinkMacSystemFont,'Segoe UI','Ubuntu','Arial',sans-serif; --success-color:#4caf50; --error-color:#f44336; --warning-color:#ff9800; }\n" +
"    html, body{ height:100%; margin:0; background:var(--background-color); color:var(--text-color); font-family:var(--font-family); overflow-x:hidden }\n" +
"    #app-container{ display: flex; flex-direction: column; height: 100vh; }\n" +
"    #player-container{ position:relative; flex: 1; width:100%; min-height: 300px; }\n" +
"    .video-js{ width:100%!important; height:100%!important }\n" +
"    .vjs-big-play-button{ border-radius:50%; background:rgba(43,51,63,.7)!important; border:none; height:2em!important; width:2em!important; margin-top:-1em!important; margin-left:-1em!important }\n" +
"    #control-panel{ position:absolute; top:0; left:0; right:0; display:flex; justify-content:center; padding:20px; z-index:100; transition:transform .4s ease,opacity .4s ease; max-height: 80vh; overflow-y: auto; }\n" +
"    #control-panel.hidden{ transform:translateY(-150%); opacity:0; pointer-events:none }\n" +
"    #input-box{ background:var(--surface-color); padding:18px 20px; border-radius:var(--border-radius); box-shadow:0 10px 30px rgba(0,0,0,.35); display:flex; flex-direction:column; gap:15px; width:92%; max-width:820px; }\n" +
"    #error-message, #success-message, #acestream-error-banner { padding:15px; border-radius:var(--border-radius); text-align:center; display: none; }\n" +
"    #error-message, #acestream-error-banner { color:#ffcccc; background:rgba(255,68,68,.12); border:1px solid rgba(255,102,102,.35); }\n" +
"    #acestream-error-banner { display: flex; align-items: center; justify-content: space-between; }\n" +
"    #acestream-error-banner button { background: var(--error-color); color: white; border: none; padding: 8px 12px; border-radius: 4px; cursor: pointer; font-weight: bold; margin-left: 15px; white-space: nowrap; }\n" +
"    #success-message{ color:#ccffcc; background:rgba(68,255,68,.12); border:1px solid rgba(102,255,102,.35); }\n" +
"    .input-group { display: flex; gap: 8px; align-items: center; flex-wrap: wrap; }\n" +
"    #acestream-link-input, #m3u-url-input{ flex:1; min-width: 300px; padding:10px 12px; border-radius:8px; background:#2a2a2a; border:1px solid #333; color:var(--text-color); font-size:15px }\n" +
"    .btn{ background:var(--primary-color); color:#fff; border:none; padding:10px 14px; border-radius:8px; cursor:pointer; font-weight:600; transition: all 0.2s; white-space: nowrap; display: flex; align-items: center; justify-content: center; gap: 6px; }\n" +
"    .btn:hover{ background:var(--primary-hover-color); transform: translateY(-1px); }\n" +
"    .btn.secondary{ background:transparent; border:1px solid #333; color:var(--text-color) }\n" +
"    .btn.secondary:hover{ background: rgba(255,255,255,0.05); }\n" +
"    .btn:disabled { opacity: 0.5; cursor: not-allowed; transform: none; }\n" +
"    .file-input-wrapper { position: relative; overflow: hidden; display: inline-block; }\n" +
"    .file-input-wrapper input[type=file] { position: absolute; left: -9999px; }\n" +
"    .file-input-label { background: var(--primary-color); color: white; padding: 10px 14px; border-radius: 8px; cursor: pointer; font-weight: 600; display: flex; align-items: center; gap: 6px; transition: all 0.2s; }\n" +
"    .file-input-label:hover { background: var(--primary-hover-color); transform: translateY(-1px); }\n" +
"    #toggle-panel-btn, #back-panel-btn { position:absolute; top:16px; z-index:101; cursor:pointer; background:rgba(30,30,30,.85); padding:10px; border-radius:50%; transition: transform 0.2s; }\n" +
"    #toggle-panel-btn { right:16px; }\n" +
"    #back-panel-btn { left:16px; display: none; }\n" +
"    #toggle-panel-btn:hover, #back-panel-btn:hover { transform: scale(1.1); }\n" +
"    #toggle-panel-btn i, #back-panel-btn i { color: var(--text-color); }\n" +
"    .author-credit { text-align: center; font-size: 12px; color: var(--text-muted-color); margin-top: -10px; margin-bottom: 10px; }\n" +
"    .small-note{ font-size:12px; color:var(--text-muted-color); text-align:center }\n" +
"    .tabs { display: flex; margin-bottom: 10px; border-bottom: 1px solid #333; flex-wrap: wrap; }\n" +
"    .tab { padding: 8px 16px; cursor: pointer; border-bottom: 2px solid transparent; transition: all 0.2s; }\n" +
"    .tab.active { border-bottom-color: var(--primary-color); color: var(--primary-color); }\n" +
"    .tab-content { display: none; }\n" +
"    .tab-content.active { display: block; }\n" +
"    .channel-list { max-height: 200px; overflow-y: auto; background: rgba(0,0,0,0.2); border-radius: 8px; padding: 8px; }\n" +
"    .channel-item { padding: 8px; border-bottom: 1px solid rgba(255,255,255,0.1); transition: background 0.2s; display: flex; justify-content: space-between; align-items: center; }\n" +
"    .channel-item:hover { background: rgba(255,255,255,0.1); }\n" +
"    .channel-item:last-child { border-bottom: none; }\n" +
"    .channel-info { flex: 1; display: flex; align-items: center; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }\n" +
"    .channel-logo { width: 24px; height: 24px; margin-right: 8px; border-radius: 4px; object-fit: cover; }\n" +
"    .channel-name { font-weight: 500; }\n" +
"    .channel-actions { display: flex; gap: 4px; }\n" +
"    .channel-actions button { background: transparent; border: none; color: var(--text-muted-color); cursor: pointer; font-size: 14px; padding: 6px; border-radius: 4px; transition: all 0.2s; width: 32px; height: 32px; display: flex; align-items: center; justify-content: center; }\n" +
"    .channel-actions button:hover { color: var(--primary-color); background: rgba(255,255,255,0.05); }\n" +
"    .empty-list { text-align: center; padding: 20px; color: var(--text-muted-color); }\n" +
"    #player-status { position: absolute; bottom: 70px; left: 10px; background: rgba(0,0,0,0.7); padding: 5px 10px; border-radius: 4px; font-size: 12px; z-index: 10; display: none; }\n" +
"    .loading-spinner { display: inline-block; width: 12px; height: 12px; border: 2px solid rgba(255,255,255,0.3); border-radius: 50%; border-top-color: var(--primary-color); animation: spin 1s ease-in-out infinite; margin-right: 5px; }\n" +
"    @keyframes spin { to { transform: rotate(360deg); } }\n" +
"    .notification { position: fixed; bottom: 20px; right: 20px; background: var(--surface-color); padding: 12px 20px; border-radius: var(--border-radius); box-shadow: 0 4px 12px rgba(0,0,0,0.3); transform: translateX(120%); transition: transform 0.3s ease; z-index: 1000; max-width: 300px; }\n" +
"    .notification.show { transform: translateX(0); }\n" +
"    .notification.success { border-left: 4px solid var(--success-color); }\n" +
"    .notification.error { border-left: 4px solid var(--error-color); }\n" +
"    .notification.warning { border-left: 4px solid var(--warning-color); }\n" +
"    .modal { display: none; position: fixed; z-index: 1000; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.7); align-items: center; justify-content: center; }\n" +
"    .modal.show { display: flex; }\n" +
"    .modal-content { background-color: var(--surface-color); padding: 20px; border-radius: var(--border-radius); box-shadow: 0 5px 15px rgba(0,0,0,0.5); width: 90%; max-width: 700px; max-height: 80vh; overflow-y: auto; }\n" +
"    .modal-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; padding-bottom: 10px; border-bottom: 1px solid #333; }\n" +
"    .modal-title { font-size: 20px; font-weight: 600; }\n" +
"    .close-modal { background: none; border: none; color: var(--text-muted-color); font-size: 24px; cursor: pointer; line-height: 1; }\n" +
"    .close-modal:hover { color: var(--text-color); }\n" +
"    .modal-description { color: var(--text-muted-color); margin-bottom: 20px; text-align: center; font-size: 14px; }\n" +
"    .app-options { display: grid; grid-template-columns: repeat(auto-fill, minmax(150px, 1fr)); gap: 12px; }\n" +
"    .app-option { display: flex; flex-direction: column; align-items: center; padding: 15px 10px; border-radius: 8px; cursor: pointer; transition: all 0.2s; border: 1px solid transparent; text-align: center; }\n" +
"    .app-option:hover { background: rgba(255,255,255,0.1); border-color: var(--primary-color); transform: translateY(-2px); }\n" +
"    .app-option.primary { border-color: var(--primary-color); background: rgba(0, 170, 255, 0.1); }\n" +
"    .app-option i { font-size: 32px; margin-bottom: 8px; color: var(--primary-color); }\n" +
"    .app-option span { font-size: 14px; font-weight: 500; }\n" +
"    .app-option small { font-size: 11px; color: var(--text-muted-color); margin-top: 4px; }\n" +
"    @media (max-width: 768px) { #input-box { width: 98%; } .app-options { grid-template-columns: repeat(2, 1fr); } .input-group { flex-direction: column; } .input-group > * { width: 100%; } .tabs { justify-content: center; } }\n" +
"  </style>\n" +
"</head>\n" +
"<body>\n" +
"  <div id=\"app-container\">\n" +
"    <div id=\"player-container\">\n" +
"      <div id=\"back-panel-btn\" title=\"Mostrar panel\">\n" +
"        <i class=\"fas fa-arrow-left\"></i>\n" +
"      </div>\n" +
"\n" +
"      <div id=\"toggle-panel-btn\" title=\"Ocultar panel\">\n" +
"        <i class=\"fas fa-eye\"></i>\n" +
"      </div>\n" +
"\n" +
"      <video id=\"video\" class=\"video-js vjs-default-skin\" controls preload=\"auto\" data-setup='{\"fluid\": true}'></video>\n" +
"      \n" +
"      <div id=\"player-status\">\n" +
"        <span class=\"loading-spinner\"></span>\n" +
"        <span id=\"status-text\">Cargando...</span>\n" +
"      </div>\n" +
"\n" +
"      <div id=\"control-panel\">\n" +
"        <div id=\"input-box\">\n" +
"          <div id=\"acestream-error-banner\">\n" +
"            <span>\n" +
"              <i class=\"fas fa-exclamation-triangle\"></i> \n" +
"              El motor de Acestream no está conectado. No se pueden reproducir enlaces Acestream en el reproductor web.\n" +
"            </span>\n" +
"            <button onclick=\"window.open('https://docs.acestream.net/products', '_blank')\">\n" +
"              <i class=\"fas fa-download\"></i> Descargar\n" +
"            </button>\n" +
"          </div>\n" +
"\n" +
"          <div id=\"error-message\"></div>\n" +
"          <div id=\"success-message\"></div>\n" +
"\n" +
"          <div class=\"tabs\">\n" +
"            <div class=\"tab active\" data-tab=\"input\">Enlace Directo</div>\n" +
"            <div class=\"tab\" data-tab=\"m3u\">Lista M3U</div>\n" +
"            <div class=\"tab\" data-tab=\"favorites\">Favoritos</div>\n" +
"            <div class=\"tab\" data-tab=\"history\">Historial</div>\n" +
"            <div class=\"tab\" data-tab=\"explore\">Explorar</div>\n" +
"          </div>\n" +
"          \n" +
"          <div class=\"author-credit\">by ck_alka</div>\n" +
"          \n" +
"          <div id=\"input-tab\" class=\"tab-content active\">\n" +
"            <form id=\"input-form\" autocomplete=\"off\">\n" +
"              <input id=\"acestream-link-input\" type=\"text\" placeholder=\"Pega un enlace (acestream://, https://... .m3u8/.mp4)\" />\n" +
"            </form>\n" +
"            \n" +
"            <div class=\"input-group\">\n" +
"              <button id=\"play-web-button\" class=\"btn\">\n" +
"                <i class=\"fas fa-play\"></i> Reproducir en Web\n" +
"              </button>\n" +
"              <button id=\"open-with-button\" class=\"btn secondary\">\n" +
"                <i class=\"fas fa-external-link-alt\"></i> Abrir con...\n" +
"              </button>\n" +
"            </div>\n" +
"\n" +
"            <div style=\"display:flex;gap:8px;justify-content:center\">\n" +
"              <button id=\"clear-button\" class=\"btn secondary\"><i class=\"fas fa-eraser\"></i> Limpiar</button>\n" +
"              <button id=\"add-favorite-button\" class=\"btn secondary\" disabled><i class=\"fas fa-star\"></i> Añadir a favoritos</button>\n" +
"            </div>\n" +
"\n" +
"            <div class=\"small-note\">También puedes abrir con <code>?url=ENLACE</code> en la barra de direcciones</div>\n" +
"          </div>\n" +
"          \n" +
"          <div id=\"m3u-tab\" class=\"tab-content\">\n" +
"            <form id=\"m3u-form\" autocomplete=\"off\">\n" +
"              <input id=\"m3u-url-input\" type=\"text\" placeholder=\"URL de la lista M3U/M3U8\" />\n" +
"              <button id=\"load-m3u-button\" class=\"btn\" type=\"submit\"><i class=\"fas fa-download\"></i> Cargar desde URL</button>\n" +
"            </form>\n" +
"\n" +
"            <div class=\"input-group\" style=\"margin-top: 10px;\">\n" +
"              <div class=\"file-input-wrapper\">\n" +
"                <input type=\"file\" id=\"m3u-file-input\" accept=\".m3u,.m3u8\" />\n" +
"                <label for=\"m3u-file-input\" class=\"file-input-label\">\n" +
"                  <i class=\"fas fa-folder-open\"></i> Cargar desde Archivo\n" +
"                </label>\n" +
"              </div>\n" +
"            </div>\n" +
"            \n" +
"            <div id=\"m3u-channels\" class=\"channel-list\">\n" +
"              <div class=\"empty-list\">Carga una lista M3U para ver los canales</div>\n" +
"            </div>\n" +
"          </div>\n" +
"          \n" +
"          <div id=\"favorites-tab\" class=\"tab-content\">\n" +
"            <div id=\"favorites-list\" class=\"channel-list\">\n" +
"              <div class=\"empty-list\">No tienes canales favoritos guardados</div>\n" +
"            </div>\n" +
"          </div>\n" +
"          \n" +
"          <div id=\"history-tab\" class=\"tab-content\">\n" +
"            <div id=\"history-list\" class=\"channel-list\">\n" +
"              <div class=\"empty-list\">No hay historial de reproducción</div>\n" +
"            </div>\n" +
"          </div>\n" +
"\n" +
"          <div id=\"explore-tab\" class=\"tab-content\">\n" +
"            <div style=\"text-align: center; padding: 20px;\">\n" +
"              <p style=\"color: var(--text-muted-color); margin-bottom: 20px;\">Visita nuestras páginas web recomendadas para encontrar más contenido.</p>\n" +
"              <button id=\"visit-futbol-btn\" class=\"btn\" style=\"font-size: 1.2em; padding: 15px 25px;\">\n" +
"                <i class=\"fas fa-futbol\"></i> Visitar FutbolenlaTV\n" +
"              </button>\n" +
"            </div>\n" +
"          </div>\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"  </div>\n" +
"\n" +
"  <div id=\"app-modal\" class=\"modal\">\n" +
"    <div class=\"modal-content\">\n" +
"      <div class=\"modal-header\">\n" +
"        <h3 class=\"modal-title\">Elegir aplicación</h3>\n" +
"        <button class=\"close-modal\">&times;</button>\n" +
"      </div>\n" +
"      <p class=\"modal-description\">\n" +
"        <strong>Importante:</strong> Una página web no puede ver las aplicaciones de tu dispositivo. \n" +
"        Usa la opción <strong>\"Intent (Android)\"</strong> para que tu ANDROID muestre un menú con <strong>TODAS</strong> las apps compatibles que tengas instaladas.\n" +
"      </p>\n" +
"      <div class=\"app-options\" id=\"app-options-container\"></div>\n" +
"    </div>\n" +
"  </div>\n" +
"\n" +
"  <div id=\"notification\" class=\"notification\"></div>\n" +
"\n" +
"  <script src=\"https://vjs.zencdn.net/8.23.3/video.min.js\"></script>\n" +
"\n" +
"  <script>\n" +
"    (function(){\n" +
"      const BASE_STREAM_URL = 'http://127.0.0.1:6878/ace/manifest.m3u8?transcode_audio=1&id=';\n" +
"      const ACE_PREFIX = 'acestream://';\n" +
"      \n" +
"      const STORAGE_KEYS = {\n" +
"        favorites: 'acestream_favorites',\n" +
"        history: 'acestream_history',\n" +
"      };\n" +
"\n" +
"      const player = videojs('video', { \n" +
"        autoplay: false,\n" +
"        controls: true,\n" +
"        fluid: true,\n" +
"        playbackRates: [0.5, 1, 1.25, 1.5, 2],\n" +
"      });\n" +
"      \n" +
"      // Elementos del DOM\n" +
"      const toggleBtn = document.getElementById('toggle-panel-btn');\n" +
"      const backBtn = document.getElementById('back-panel-btn');\n" +
"      const controlPanel = document.getElementById('control-panel');\n" +
"      const inputForm = document.getElementById('input-form');\n" +
"      const m3uForm = document.getElementById('m3u-form');\n" +
"      const linkInput = document.getElementById('acestream-link-input');\n" +
"      const m3uUrlInput = document.getElementById('m3u-url-input');\n" +
"      const m3uFileInput = document.getElementById('m3u-file-input');\n" +
"      const errorMessage = document.getElementById('error-message');\n" +
"      const successMessage = document.getElementById('success-message');\n" +
"      const acestreamErrorBanner = document.getElementById('acestream-error-banner');\n" +
"      const clearBtn = document.getElementById('clear-button');\n" +
"      const addFavoriteBtn = document.getElementById('add-favorite-button');\n" +
"      const playWebBtn = document.getElementById('play-web-button');\n" +
"      const openWithBtn = document.getElementById('open-with-button');\n" +
"      const visitFutbolBtn = document.getElementById('visit-futbol-btn');\n" +
"      const playerStatus = document.getElementById('player-status');\n" +
"      const statusText = document.getElementById('status-text');\n" +
"      const notification = document.getElementById('notification');\n" +
"      const appModal = document.getElementById('app-modal');\n" +
"      const closeModalBtn = document.querySelector('.close-modal');\n" +
"      const appOptionsContainer = document.getElementById('app-options-container');\n" +
"      \n" +
"      // Tabs\n" +
"      const tabs = document.querySelectorAll('.tab');\n" +
"      const tabContents = document.querySelectorAll('.tab-content');\n" +
"      \n" +
"      // Listas\n" +
"      const m3uChannels = document.getElementById('m3u-channels');\n" +
"      const favoritesList = document.getElementById('favorites-list');\n" +
"      const historyList = document.getElementById('history-list');\n" +
"      \n" +
"      // Estado\n" +
"      let visible = true;\n" +
"      let currentChannel = null;\n" +
"      let favorites = JSON.parse(localStorage.getItem(STORAGE_KEYS.favorites)) || [];\n" +
"      let history = JSON.parse(localStorage.getItem(STORAGE_KEYS.history)) || [];\n" +
"      let m3uChannelsData = [];\n" +
"      let channelUrlToOpen = '';\n" +
"\n" +
"      // Inicialización\n" +
"      function init() {\n" +
"        setupEventListeners();\n" +
"        updateFavoritesList();\n" +
"        updateHistoryList();\n" +
"        \n" +
"        const external = getQueryParam('url') || getQueryParam('u');\n" +
"        if(external) {\n" +
"          try { loadLink(decodeURIComponent(external)); } catch(e) { loadLink(external); }\n" +
"        }\n" +
"      }\n" +
"\n" +
"      function setupEventListeners() {\n" +
"        toggleBtn.addEventListener('click', () => togglePanel());\n" +
"        backBtn.addEventListener('click', () => togglePanel(true));\n" +
"        \n" +
"        inputForm.addEventListener('submit', (ev) => {\n" +
"          ev.preventDefault();\n" +
"          handlePlayWeb();\n" +
"        });\n" +
"        \n" +
"        m3uForm.addEventListener('submit', (ev) => {\n" +
"          ev.preventDefault();\n" +
"          loadM3UList(m3uUrlInput.value.trim());\n" +
"        });\n" +
"\n" +
"        m3uFileInput.addEventListener('change', (ev) => {\n" +
"          const file = ev.target.files[0];\n" +
"          if (file) {\n" +
"            loadM3UFromFile(file);\n" +
"          }\n" +
"        });\n" +
"        \n" +
"        playWebBtn.addEventListener('click', handlePlayWeb);\n" +
"        openWithBtn.addEventListener('click', handleOpenWith);\n" +
"        \n" +
"        clearBtn.addEventListener('click', () => {\n" +
"          linkInput.value = '';\n" +
"          hideMessages();\n" +
"          addFavoriteBtn.disabled = true;\n" +
"        });\n" +
"        \n" +
"        addFavoriteBtn.addEventListener('click', () => {\n" +
"          if (currentChannel) addToFavorites(currentChannel);\n" +
"        });\n" +
"        \n" +
"        tabs.forEach(tab => {\n" +
"          tab.addEventListener('click', () => switchTab(tab.getAttribute('data-tab')));\n" +
"        });\n" +
"\n" +
"        visitFutbolBtn.addEventListener('click', () => {\n" +
"          window.open('https://www.futbolenlatv.es/deporte', '_blank');\n" +
"        });\n" +
"        \n" +
"        closeModalBtn.addEventListener('click', () => appModal.classList.remove('show'));\n" +
"        \n" +
"        appModal.addEventListener('click', (e) => {\n" +
"          if (e.target === appModal) appModal.classList.remove('show');\n" +
"        });\n" +
"        \n" +
"        document.addEventListener('keydown', (e) => {\n" +
"          if (e.key === 'Escape') {\n" +
"            appModal.classList.remove('show');\n" +
"            togglePanel(true);\n" +
"          }\n" +
"        });\n" +
"        \n" +
"        player.on('loadstart', () => showStatus('Cargando...'));\n" +
"        player.on('canplay', () => hideStatus());\n" +
"        \n" +
"        player.on('error', () => {\n" +
"          hideStatus();\n" +
"          const error = player.error();\n" +
"          const errorText = error ? error.message : 'Error desconocido';\n" +
"          \n" +
"          if (currentChannel && currentChannel.type === 'ace') {\n" +
"            handleAcestreamEngineError(errorText);\n" +
"          } else {\n" +
"            showError(`Error al cargar el stream. Verifica la conexión o el enlace. Error: ${errorText}`);\n" +
"          }\n" +
"        });\n" +
"      }\n" +
"\n" +
"      function handleAcestreamEngineError(errorText) {\n" +
"        acestreamErrorBanner.style.display = 'flex';\n" +
"        console.error('Acestream Engine Error:', errorText);\n" +
"      }\n" +
"      \n" +
"      function handlePlayWeb() {\n" +
"        const raw = linkInput.value.trim();\n" +
"        if (!raw) {\n" +
"          showError('Por favor, introduce un enlace.');\n" +
"          return;\n" +
"        }\n" +
"        loadLink(raw, { userAction: true });\n" +
"      }\n" +
"\n" +
"      function handleOpenWith() {\n" +
"        const raw = linkInput.value.trim();\n" +
"        if (!raw) {\n" +
"          showError('Por favor, introduce un enlace para \"Abrir con...\".');\n" +
"          return;\n" +
"        }\n" +
"        channelUrlToOpen = raw;\n" +
"        showOpenWithDialog(raw);\n" +
"      }\n" +
"\n" +
"      function showOpenWithDialog(url) {\n" +
"        const parsed = parseLink(url);\n" +
"        if (!parsed) {\n" +
"          showError('Enlace inválido.');\n" +
"          return;\n" +
"        }\n" +
"\n" +
"        const apps = [\n" +
"          { id: 'intent', name: 'Intent (Android)', icon: 'fa-robot', scheme: 'intent://', note: 'MENÚ DE ANDROID para elegir TODAS tus apps', primary: true },\n" +
"          { id: 'vlc', name: 'VLC', icon: 'fa-play-circle', scheme: 'vlc://', note: 'Popular, todos los formatos' },\n" +
"          { id: 'mx', name: 'MX Player', icon: 'fa-film', scheme: 'mxplayer://', note: 'Soporte de hardware' },\n" +
"          { id: 'nplayer', name: 'nPlayer', icon: 'fa-play', scheme: 'nplayer-', note: 'Potente reproductor' },\n" +
"          { id: 'infuse', name: 'Infuse', icon: 'fa-fire', scheme: 'infuse://x-callback-url/play?url=', note: 'Para iOS / Apple TV' },\n" +
"          { id: 'gse', name: 'GSE IPTV', icon: 'fa-tv', scheme: 'gse://', note: 'Smart TV / Android' },\n" +
"          { id: 'bsplayer', name: 'BSPlayer', icon: 'fa-play', scheme: 'bsplayer://', note: 'Reproductor ligero' },\n" +
"          { id: 'wvc', name: 'Web Video Caster', icon: 'fa-cast', scheme: 'wvc://', note: 'Casta a TV/Chromecast' },\n" +
"          { id: 'localcast', name: 'LocalCast', icon: 'fa-broadcast-tower', scheme: 'localcast://', note: 'Casta a dispositivos locales' },\n" +
"          { id: 'justplay', name: 'Just Play', icon: 'fa-play', scheme: 'justplay://', note: 'Simple y rápido' },\n" +
"          { id: 'kodi', name: 'Kodi', icon: 'fa-cube', scheme: 'kodi://play/video/', note: 'Centro multimedia' },\n" +
"          { id: 'pot', name: 'PotPlayer', icon: 'fa-tv', scheme: 'potplayer://', note: 'Windows/Android' },\n" +
"          { id: 'soda', name: 'Soda Player', icon: 'fa-tv-retro', scheme: 'sodaplayer://', note: 'PC/Mac, streaming P2P' },\n" +
"          { id: 'ace', name: 'Acestream', icon: 'fa-broadcast-tower', scheme: 'acestream://', note: 'Solo enlaces acestream://' },\n" +
"          { id: 'copy', name: 'Copiar Enlace', icon: 'fa-copy', scheme: 'copy', note: 'Al portapapeles' },\n" +
"        ];\n" +
"\n" +
"        appOptionsContainer.innerHTML = '';\n" +
"        \n" +
"        apps.forEach(app => {\n" +
"          if (app.id === 'ace' && parsed.type !== 'ace') return;\n" +
"\n" +
"          const option = document.createElement('div');\n" +
"          option.className = `app-option ${app.primary ? 'primary' : ''}`;\n" +
"          option.innerHTML = `\n" +
"            <i class=\"fas ${app.icon}\"></i>\n" +
"            <span>${app.name}</span>\n" +
"            <small>${app.note}</small>\n" +
"          `;\n" +
"          \n" +
"          option.addEventListener('click', () => {\n" +
"            appModal.classList.remove('show');\n" +
"            openWithApp(channelUrlToOpen, app.scheme, parsed);\n" +
"          });\n" +
"          \n" +
"          appOptionsContainer.appendChild(option);\n" +
"        });\n" +
"\n" +
"        appModal.classList.add('show');\n" +
"      }\n" +
"\n" +
"      function openWithApp(raw, scheme, parsed) {\n" +
"        let finalUrl = '';\n" +
"        \n" +
"        if (scheme === 'copy') {\n" +
"          copyToClipboard(raw);\n" +
"          return;\n" +
"        }\n" +
"\n" +
"        if (scheme === 'acestream://') {\n" +
"          finalUrl = raw;\n" +
"        } else if (scheme === 'intent://') {\n" +
"          const streamUrl = (parsed.type === 'ace') ? BASE_STREAM_URL + parsed.id : raw;\n" +
"          finalUrl = `intent://${encodeURIComponent(streamUrl)}#Intent;scheme=http;action=android.intent.action.VIEW;end`;\n" +
"        } else if (scheme === 'kodi://play/video/') {\n" +
"          const streamUrl = (parsed.type === 'ace') ? BASE_STREAM_URL + parsed.id : raw;\n" +
"          finalUrl = scheme + encodeURIComponent(streamUrl);\n" +
"        } else {\n" +
"          const streamUrl = (parsed.type === 'ace') ? BASE_STREAM_URL + parsed.id : raw;\n" +
"          finalUrl = scheme + encodeURIComponent(streamUrl);\n" +
"        }\n" +
"        \n" +
"        window.open(finalUrl, '_blank');\n" +
"        showNotification(`Abriendo con ${scheme.replace('://', '').replace('/play/video/', '')}...`, 'success');\n" +
"        \n" +
"        currentChannel = {\n" +
"          name: parsed.type === 'ace' ? `Acestream ${parsed.id.substr(0,8)}` : new URL(raw).hostname,\n" +
"          url: raw,\n" +
"          type: parsed.type\n" +
"        };\n" +
"        addToHistory(currentChannel);\n" +
"      }\n" +
"\n" +
"      function switchTab(tabId) {\n" +
"        tabs.forEach(tab => {\n" +
"          tab.classList.toggle('active', tab.getAttribute('data-tab') === tabId);\n" +
"        });\n" +
"        tabContents.forEach(content => {\n" +
"          content.classList.toggle('active', content.id === `${tabId}-tab`);\n" +
"        });\n" +
"      }\n" +
"\n" +
"      function togglePanel(force) {\n" +
"        visible = (typeof force === 'boolean') ? force : !visible;\n" +
"        controlPanel.classList.toggle('hidden', !visible);\n" +
"        backBtn.style.display = visible ? 'none' : 'flex';\n" +
"      }\n" +
"\n" +
"      function hideMessages() {\n" +
"        hideError();\n" +
"        hideSuccess();\n" +
"        acestreamErrorBanner.style.display = 'none';\n" +
"      }\n" +
"\n" +
"      function showError(text) {\n" +
"        errorMessage.innerText = text;\n" +
"        errorMessage.style.display = 'block';\n" +
"        hideSuccess();\n" +
"        acestreamErrorBanner.style.display = 'none';\n" +
"      }\n" +
"      function hideError() { errorMessage.style.display = 'none'; }\n" +
"      \n" +
"      function showSuccess(text) {\n" +
"        successMessage.innerText = text;\n" +
"        successMessage.style.display = 'block';\n" +
"        hideError();\n" +
"        acestreamErrorBanner.style.display = 'none';\n" +
"      }\n" +
"      function hideSuccess() { successMessage.style.display = 'none'; }\n" +
"      \n" +
"      function showStatus(text) {\n" +
"        statusText.textContent = text;\n" +
"        playerStatus.style.display = 'block';\n" +
"      }\n" +
"      function hideStatus() { playerStatus.style.display = 'none'; }\n" +
"      \n" +
"      function showNotification(message, type = 'success') {\n" +
"        notification.textContent = message;\n" +
"        notification.className = `notification ${type} show`;\n" +
"        setTimeout(() => notification.classList.remove('show'), 3000);\n" +
"      }\n" +
"\n" +
"      function getQueryParam(name) {\n" +
"        return new URLSearchParams(location.search).get(name);\n" +
"      }\n" +
"\n" +
"      function parseLink(raw) {\n" +
"        if (!raw) return null;\n" +
"        raw = raw.trim();\n" +
"        if (raw.startsWith(ACE_PREFIX)) return { type: 'ace', id: raw.substring(ACE_PREFIX.length) };\n" +
"        if (raw.startsWith('http://') || raw.startsWith('https://')) return { type: 'http', url: raw };\n" +
"        if (/^[0-9a-fA-F]{6,}$/.test(raw)) return { type: 'ace', id: raw };\n" +
"        return null;\n" +
"      }\n" +
"\n" +
"      function loadLink(raw, opts = {}) {\n" +
"        hideMessages();\n" +
"        const parsed = parseLink(raw);\n" +
"        if (!parsed) {\n" +
"          showError('Enlace inválido.');\n" +
"          return;\n" +
"        }\n" +
"\n" +
"        let src;\n" +
"        if(parsed.type === 'ace') {\n" +
"          src = BASE_STREAM_URL + encodeURIComponent(parsed.id);\n" +
"        } else {\n" +
"          src = parsed.url;\n" +
"        }\n" +
"        \n" +
"        currentChannel = {\n" +
"          name: parsed.type === 'ace' ? `Acestream ${parsed.id.substr(0,8)}` : new URL(raw).hostname,\n" +
"          url: raw,\n" +
"          type: parsed.type\n" +
"        };\n" +
"        \n" +
"        document.title = `${currentChannel.name} - Reproductor Universal`;\n" +
"        addFavoriteBtn.disabled = false;\n" +
"        addToHistory(currentChannel);\n" +
"\n" +
"        try {\n" +
"          player.src({ src: src, type: 'application/x-mpegURL' });\n" +
"          const playPromise = player.play();\n" +
"          if (playPromise !== undefined) {\n" +
"            playPromise.then(() => {\n" +
"              togglePanel(false);\n" +
"              if (opts.userAction) showNotification('Reproduciendo canal', 'success');\n" +
"            }).catch(err => {\n" +
"              console.warn('Play rejected:', err);\n" +
"              if(opts.userAction) {\n" +
"                showNotification('No se pudo reproducir automáticamente. Presiona Play.', 'warning');\n" +
"              } else {\n" +
"                showError('Interactúa con la página para iniciar la reproducción.');\n" +
"              }\n" +
"              togglePanel(true);\n" +
"            });\n" +
"          }\n" +
"        } catch(e) {\n" +
"          console.error(e);\n" +
"          showError('Error cargando el reproductor.');\n" +
"        }\n" +
"      }\n" +
"      \n" +
"      async function copyToClipboard(text) {\n" +
"        try {\n" +
"          await navigator.clipboard.writeText(text);\n" +
"          showNotification('Enlace copiado al portapapeles', 'success');\n" +
"        } catch (err) {\n" +
"          console.error('Error al copiar: ', err);\n" +
"          showError('No se pudo copiar el enlace');\n" +
"        }\n" +
"      }\n" +
"\n" +
"      async function loadM3UList(url) {\n" +
"        if (!url) { showError('Introduce una URL válida para la lista M3U'); return; }\n" +
"        showStatus('Cargando lista M3U desde URL...');\n" +
"        try {\n" +
"          const response = await fetch(url);\n" +
"          if (!response.ok) throw new Error(`Error al cargar la lista: ${response.status}`);\n" +
"          const text = await response.text();\n" +
"          processM3UData(text);\n" +
"        } catch (error) {\n" +
"          console.error('Error loading M3U:', error);\n" +
"          showError(`Error al cargar la lista M3U: ${error.message}`);\n" +
"        } finally {\n" +
"          hideStatus();\n" +
"        }\n" +
"      }\n" +
"\n" +
"      function loadM3UFromFile(file) {\n" +
"        showStatus('Cargando lista M3U desde archivo...');\n" +
"        const reader = new FileReader();\n" +
"        reader.onload = function(e) {\n" +
"          processM3UData(e.target.result);\n" +
"          hideStatus();\n" +
"        };\n" +
"        reader.onerror = function(e) {\n" +
"          console.error('Error reading file:', e);\n" +
"          showError('No se pudo leer el archivo seleccionado.');\n" +
"          hideStatus();\n" +
"        };\n" +
"        reader.readAsText(file);\n" +
"      }\n" +
"      \n" +
"      function processM3UData(text) {\n" +
"        m3uChannelsData = parseM3U(text);\n" +
"        if (m3uChannelsData.length === 0) { \n" +
"          showError('No se encontraron canales en la lista M3U'); \n" +
"          return; \n" +
"        }\n" +
"        renderM3UChannels();\n" +
"        showNotification(`Se cargaron ${m3uChannelsData.length} canales`, 'success');\n" +
"      }\n" +
"      \n" +
"      function parseM3U(text) {\n" +
"        const lines = text.split('\\n');\n" +
"        const channels = [];\n" +
"        let currentChannel = {};\n" +
"        for (let i = 0; i < lines.length; i++) {\n" +
"          const line = lines[i].trim();\n" +
"          if (line.startsWith('#EXTINF:')) {\n" +
"            const nameMatch = line.match(/,(.+)$/);\n" +
"            if (nameMatch) currentChannel.name = nameMatch[1];\n" +
"            const logoMatch = line.match(/tvg-logo=\"([^\"]+)\"/);\n" +
"            if (logoMatch) currentChannel.logo = logoMatch[1];\n" +
"          } else if (line && !line.startsWith('#')) {\n" +
"            currentChannel.url = line;\n" +
"            channels.push({...currentChannel});\n" +
"            currentChannel = {};\n" +
"          }\n" +
"        }\n" +
"        return channels;\n" +
"      }\n" +
"      \n" +
"      function renderM3UChannels() {\n" +
"        if (m3uChannelsData.length === 0) {\n" +
"          m3uChannels.innerHTML = '<div class=\"empty-list\">No hay canales disponibles</div>';\n" +
"          return;\n" +
"        }\n" +
"        m3uChannels.innerHTML = '';\n" +
"        m3uChannelsData.forEach((channel, index) => {\n" +
"          m3uChannels.appendChild(createChannelItem(channel, index, 'm3u'));\n" +
"        });\n" +
"      }\n" +
"      \n" +
"      function createChannelItem(channel, index, context) {\n" +
"        const channelItem = document.createElement('div');\n" +
"        channelItem.className = 'channel-item';\n" +
"        \n" +
"        const channelInfo = document.createElement('div');\n" +
"        channelInfo.className = 'channel-info';\n" +
"        \n" +
"        if (channel.logo) {\n" +
"          const logo = document.createElement('img');\n" +
"          logo.src = channel.logo;\n" +
"          logo.className = 'channel-logo';\n" +
"          logo.onerror = function() { this.style.display = 'none'; };\n" +
"          channelInfo.appendChild(logo);\n" +
"        }\n" +
"        \n" +
"        const nameSpan = document.createElement('span');\n" +
"        nameSpan.className = 'channel-name';\n" +
"        nameSpan.textContent = channel.name || `Canal ${index + 1}`;\n" +
"        channelInfo.appendChild(nameSpan);\n" +
"        \n" +
"        const actions = document.createElement('div');\n" +
"        actions.className = 'channel-actions';\n" +
"        \n" +
"        const playWebBtn = document.createElement('button');\n" +
"        playWebBtn.innerHTML = '<i class=\"fas fa-globe\"></i>';\n" +
"        playWebBtn.title = 'Reproducir en Web';\n" +
"        playWebBtn.addEventListener('click', () => loadLink(channel.url, { userAction: true }));\n" +
"        \n" +
"        const openExtBtn = document.createElement('button');\n" +
"        openExtBtn.innerHTML = '<i class=\"fas fa-external-link-alt\"></i>';\n" +
"        openExtBtn.title = 'Abrir con aplicación';\n" +
"        openExtBtn.addEventListener('click', () => {\n" +
"          channelUrlToOpen = channel.url;\n" +
"          showOpenWithDialog(channel.url);\n" +
"        });\n" +
"        \n" +
"        actions.appendChild(playWebBtn);\n" +
"        actions.appendChild(openExtBtn);\n" +
"\n" +
"        if (context === 'favorites') {\n" +
"          const removeBtn = document.createElement('button');\n" +
"          removeBtn.innerHTML = '<i class=\"fas fa-trash\"></i>';\n" +
"          removeBtn.title = 'Eliminar de favoritos';\n" +
"          removeBtn.addEventListener('click', () => removeFromFavorites(index));\n" +
"          actions.appendChild(removeBtn);\n" +
"        } else if (context === 'history') {\n" +
"            const removeBtn = document.createElement('button');\n" +
"            removeBtn.innerHTML = '<i class=\"fas fa-times\"></i>';\n" +
"            removeBtn.title = 'Eliminar del historial';\n" +
"            removeBtn.addEventListener('click', () => removeFromHistory(index));\n" +
"            actions.appendChild(removeBtn);\n" +
"        } else {\n" +
"            const favBtn = document.createElement('button');\n" +
"            favBtn.innerHTML = '<i class=\"fas fa-star\"></i>';\n" +
"            favBtn.title = 'Añadir a favoritos';\n" +
"            favBtn.addEventListener('click', () => {\n" +
"              addToFavorites({\n" +
"                name: channel.name || `Canal ${index + 1}`,\n" +
"                url: channel.url,\n" +
"                type: channel.url.startsWith(ACE_PREFIX) ? 'ace' : 'http'\n" +
"              });\n" +
"            });\n" +
"            actions.appendChild(favBtn);\n" +
"        }\n" +
"        \n" +
"        channelItem.appendChild(channelInfo);\n" +
"        channelItem.appendChild(actions);\n" +
"        \n" +
"        return channelItem;\n" +
"      }\n" +
"      \n" +
"      function addToFavorites(channel) {\n" +
"        if (favorites.some(fav => fav.url === channel.url)) {\n" +
"          showNotification('El canal ya está en tus favoritos', 'warning');\n" +
"          return;\n" +
"        }\n" +
"        favorites.unshift(channel);\n" +
"        if (favorites.length > 50) favorites = favorites.slice(0, 50);\n" +
"        localStorage.setItem(STORAGE_KEYS.favorites, JSON.stringify(favorites));\n" +
"        updateFavoritesList();\n" +
"        showNotification('Añadido a favoritos (guardado en este dispositivo)', 'success');\n" +
"      }\n" +
"      \n" +
"      function updateFavoritesList() {\n" +
"        if (favorites.length === 0) {\n" +
"          favoritesList.innerHTML = '<div class=\"empty-list\">No tienes canales favoritos guardados</div>';\n" +
"          return;\n" +
"        }\n" +
"        favoritesList.innerHTML = '';\n" +
"        favorites.forEach((channel, index) => {\n" +
"          favoritesList.appendChild(createChannelItem(channel, index, 'favorites'));\n" +
"        });\n" +
"      }\n" +
"      \n" +
"      function removeFromFavorites(index) {\n" +
"        favorites.splice(index, 1);\n" +
"        localStorage.setItem(STORAGE_KEYS.favorites, JSON.stringify(favorites));\n" +
"        updateFavoritesList();\n" +
"        showNotification('Eliminado de favoritos', 'success');\n" +
"      }\n" +
"      \n" +
"      function addToHistory(channel) {\n" +
"        const existingIndex = history.findIndex(item => item.url === channel.url);\n" +
"        if (existingIndex !== -1) history.splice(existingIndex, 1);\n" +
"        history.unshift(channel);\n" +
"        if (history.length > 30) history = history.slice(0, 30);\n" +
"        localStorage.setItem(STORAGE_KEYS.history, JSON.stringify(history));\n" +
"        updateHistoryList();\n" +
"      }\n" +
"      \n" +
"      function updateHistoryList() {\n" +
"        if (history.length === 0) {\n" +
"          historyList.innerHTML = '<div class=\"empty-list\">No hay historial de reproducción</div>';\n" +
"          return;\n" +
"        }\n" +
"        historyList.innerHTML = '';\n" +
"        history.forEach((channel, index) => {\n" +
"          historyList.appendChild(createChannelItem(channel, index, 'history'));\n" +
"        });\n" +
"      }\n" +
"      \n" +
"      function removeFromHistory(index) {\n" +
"        history.splice(index, 1);\n" +
"        localStorage.setItem(STORAGE_KEYS.history, JSON.stringify(history));\n" +
"        updateHistoryList();\n" +
"      }\n" +
"\n" +
"      window.loadAceStream = function(url) { loadLink(url, { userAction: true }); };\n" +
"      window.addEventListener('error', (ev) => { console.error('Global error:', ev); showError('Error en la página. Revisa la consola.'); });\n" +
"      window.addEventListener('unhandledrejection', (ev) => { console.error('Unhandled rejection:', ev); });\n" +
"      \n" +
"      init();\n" +
"    })();\n" +
"  </script>\n" +
"</body>\n" +
"</html>";
    }
}
