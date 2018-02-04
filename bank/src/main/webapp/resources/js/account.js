
function accountLoadPage() {
    if (window.ACCOUNT_SORT_BY) {
        var el = document.getElementById(window.ACCOUNT_SORT_BY);
        if (el) {
            el.checked = true;
        }
    }

    if (window.ACCOUNT_ERROR) {
        showNewAccount();

    }
}

function accountSortClick(el) {
    window.location.href = window.ACCOUNT_URL + "?sortBy=" + el.id;
}

function accountPlus() {
    showNewAccount();

    if (window.QUERY) {
        var el = document.getElementById("addAccount");
        if (el) {
            el.action += "?" + window.QUERY;
        }
    }
}

function showNewAccount() {
    var el = document.getElementById("newAccount");
    if (el) {
        el.hidden = undefined;
    }
}