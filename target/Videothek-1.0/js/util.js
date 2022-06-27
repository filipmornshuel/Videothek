/**
 * utility functions for multiple pages
 *
 * @author  Filip Slavkovic
 * @since   2022-06-26
 * @version 1.0
 */

/**
 * get the value of an url parameter identified by key
 * source: https://www.sitepoint.com/get-url-parameters-with-javascript/
 * @param key  the key to be searched
 * @returns values as a String or null
 */
function getQueryParam (key) {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    return urlParams.get(key);
}

/**
 * gets the value of the cookie with the specified name
 * Source: https://www.w3schools.com/js/js_cookies.asp
 * @param cname  the name of the cookie
 * @returns {string}
 */
function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let cookieArray = decodedCookie.split(';');
    for(let i = 0; i <cookieArray.length; i++) {
        let cookie = cookieArray[i];
        while (cookie.charAt(0) == ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(name) == 0) {
            return cookie.substring(name.length, cookie.length);
        }
    }
    return "";
}