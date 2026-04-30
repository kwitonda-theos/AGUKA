(function () {
    const themeKey = "engineer-theme";
    const buttons = Array.from(document.querySelectorAll(".control-theme"));
    const body = document.body;

    function applyTheme(theme) {
        if (theme === "dark") {
            body.dataset.theme = "dark";
        } else {
            delete body.dataset.theme;
        }
        localStorage.setItem(themeKey, theme);
    }

    const savedTheme = localStorage.getItem(themeKey);
    if (savedTheme === "dark") {
        body.dataset.theme = "dark";
    }

    buttons.forEach((button) => {
        button.addEventListener("click", () => {
            const nextTheme = body.dataset.theme === "dark" ? "light" : "dark";
            applyTheme(nextTheme);
        });
    });
})();
