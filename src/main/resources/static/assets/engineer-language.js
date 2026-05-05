document.addEventListener("DOMContentLoaded", () => {
    (function () {
        const languageKey = "engineer-language";
        const themeKey = "engineer-theme";
        const names = {
            en: "English",
            fr: "Français",
            rw: "Kinyarwanda",
        };

        const body = document.body;
        const languageSelects = Array.from(document.querySelectorAll(".language-select"));
        const languageInitials = Array.from(document.querySelectorAll(".language-initials"));
        const languageMenus = Array.from(document.querySelectorAll(".language-menu"));
        const themeButtons = Array.from(document.querySelectorAll(".control-theme"));
        const savedLanguage = localStorage.getItem(languageKey) || "en";
        const savedTheme = localStorage.getItem(themeKey);

        const translations = {
            en: {
                nav: ["Dashboard", "Browse jobs", "Notifications", "Get Verified"],
                profile: "Profile",
                dashboard: {
                    welcome: "Welcome back,",
                    subtitle: "Here's what's happening with your account",
                    stats: ["Profile status", "Active applications", "Ratings"],
                    section: "Active jobs",
                    noJobs: "No active jobs available at the moment."
                },
                browse: {
                    title: "Browse jobs",
                    subtitle: "Find and search for posted jobs tailored to you.",
                    filters: ["Location", "Budget", "Specialization"],
                    section: "jobs",
                    apply: "Apply now",
                    view: "View Details"
                },
                notifications: {
                    title: "Notifications",
                    subtitle: "See who contacted you or responses from the jobs you applied to.",
                    noNotifs: "No notifications yet."
                },
                verification: {
                    title: "Verification Profile",
                    subtitle: "Keep your profile updated to increase your chances of being hired.",
                    sections: ["Personal information", "Professional information", "Verification Details"],
                    labels: ["Full Name", "Email", "Current Location", "Specialization", "Years of Experience", "Professional Bio", "National ID Number"],
                    save: "Update Profile",
                    submit: "Submit for Verification"
                }
            },
            fr: {
                nav: ["Tableau de bord", "Parcourir les offres", "Notifications", "Se faire vérifier"],
                profile: "Profil",
                dashboard: {
                    welcome: "Bon retour,",
                    subtitle: "Voici ce qui se passe sur votre compte",
                    stats: ["Statut du profil", "Candidatures actives", "Notes"],
                    section: "Emplois actifs",
                    noJobs: "Aucun emploi actif disponible pour le moment."
                },
                browse: {
                    title: "Parcourir les offres",
                    subtitle: "Trouvez et recherchez des emplois publiés qui vous correspondent.",
                    filters: ["Lieu", "Budget", "Spécialisation"],
                    section: "emplois",
                    apply: "Postuler",
                    view: "Détails"
                },
                notifications: {
                    title: "Notifications",
                    subtitle: "Voyez qui vous a contacté ou les réponses aux emplois auxquels vous avez postulé.",
                    noNotifs: "Aucune notification pour le moment."
                },
                verification: {
                    title: "Profil de vérification",
                    subtitle: "Maintenez votre profil à jour pour augmenter vos chances d'être embauché.",
                    sections: ["Informations personnelles", "Informations professionnelles", "Détails de vérification"],
                    labels: ["Nom complet", "E-mail", "Lieu actuel", "Spécialisation", "Années d'expérience", "Bio professionnelle", "Numéro de carte d'identité"],
                    save: "Mettre à jour le profil",
                    submit: "Soumettre pour vérification"
                }
            },
            rw: {
                nav: ["Ahabanza", "Sura imirimo", "Amatangazo", "Emezwa"],
                profile: "Umwirondoro",
                dashboard: {
                    welcome: "Mwakire neza,",
                    subtitle: "Dore ibibera kuri konte yawe",
                    stats: ["Imiterere ya konte", "Ubusabe buhari", "Amanota"],
                    section: "Imirimo iriho",
                    noJobs: "Nta mirimo ihari muri uyu mwanya."
                },
                browse: {
                    title: "Sura imirimo",
                    subtitle: "Shaka kandi ushakishe imirimo yashyizweho ihuye nawe.",
                    filters: ["Aho iri", "Ingengo y'imari", "Ubuhezanguni"],
                    section: "imirimo",
                    apply: "Saba ubu",
                    view: "Reba ibisobanuro"
                },
                notifications: {
                    title: "Amatangazo",
                    subtitle: "Reba uwakuvugishije cyangwa ibisubizo by'imirimo wasabye.",
                    noNotifs: "Nta matangazo arahari."
                },
                verification: {
                    title: "Umwirondoro w'igenzura",
                    subtitle: "Guma uvugurura umwirondoro wawe kugira ngo ube ufite amahirwe menshi yo guhabwa akazi.",
                    sections: ["Amakuru yihariye", "Amakuru y'umwuga", "Ibisobanuro by'igenzura"],
                    labels: ["Izina ryuzuye", "Imeyili", "Aho ubarizwa", "Ubuhezanguni", "Imyaka y'uburambe", "Amakuru magufi", "Nimero y'irangamuntu"],
                    save: "Vugurura umwirondoro",
                    submit: "Ohereza kugira ngo igenzurwe"
                }
            }
        };

        function setTheme(theme) {
            if (theme === "dark") {
                body.dataset.theme = "dark";
            } else {
                delete body.dataset.theme;
            }
            localStorage.setItem(themeKey, theme);
        }

        function syncLanguageUi(language) {
            languageInitials.forEach((initials) => {
                initials.textContent = (language || "en").toUpperCase();
            });

            languageMenus.forEach((menu) => {
                const selected = menu.querySelector(".language-selected");
                const items = Array.from(menu.querySelectorAll("li"));
                if (selected) {
                    selected.textContent = names[language] || names.en;
                }
                items.forEach((item) => {
                    item.classList.toggle("active", item.dataset.lang === language);
                });
            });
        }

        function applyLanguage(language) {
            try {
                const copy = translations[language] || translations.en;
                document.documentElement.lang = language;

                // Nav
                const navLabels = document.querySelectorAll(".nav-item span");
                navLabels.forEach((label, index) => {
                    if (copy.nav[index]) label.textContent = copy.nav[index];
                });

                // Skip profileLabel to avoid overwriting real engineer name

                // Dashboard specific
                const welcomeText = document.querySelector(".welcome-text");
                if (welcomeText) {
                    welcomeText.textContent = copy.dashboard.welcome;
                    const sub = document.querySelector(".sub");
                    if (sub) sub.textContent = copy.dashboard.subtitle;
                    const statTitles = document.querySelectorAll(".stat h3");
                    statTitles.forEach((title, index) => {
                        if (copy.dashboard.stats[index]) title.textContent = copy.dashboard.stats[index];
                    });
                    const section = document.querySelector(".section-title");
                    if (section) section.textContent = copy.dashboard.section;
                }

                // Browse specific
                const browseTitle = document.querySelector(".title");
                if (browseTitle && document.querySelector(".filters")) {
                    browseTitle.textContent = copy.browse.title;
                    const sub = document.querySelector(".subtitle");
                    if (sub) sub.textContent = copy.browse.subtitle;
                    const filterLabels = document.querySelectorAll(".filter label");
                    filterLabels.forEach((label, index) => {
                        if (copy.browse.filters[index]) label.textContent = copy.browse.filters[index];
                    });
                    const browseSection = document.querySelector(".section-title");
                    if (browseSection) browseSection.textContent = copy.browse.section;
                    const applyBtns = document.querySelectorAll(".btn-primary");
                    applyBtns.forEach(btn => {
                       if (btn.tagName === "BUTTON" && btn.textContent.trim() !== "") btn.textContent = copy.browse.apply;
                    });
                }

                // Notifications specific
                const notifTitle = document.querySelector(".title");
                if (notifTitle && document.querySelector(".notice")) {
                    notifTitle.textContent = copy.notifications.title;
                    const sub = document.querySelector(".subtitle");
                    if (sub) sub.textContent = copy.notifications.subtitle;
                }

                // Verification specific
                const verTitle = document.querySelector(".page-title");
                if (verTitle && document.querySelector("form")) {
                    verTitle.textContent = copy.verification.title;
                    const sub = document.querySelector(".page-sub");
                    if (sub) sub.textContent = copy.verification.subtitle;
                    const sectionTitles = document.querySelectorAll(".section-title");
                    sectionTitles.forEach((title, index) => {
                        if (copy.verification.sections[index]) title.textContent = copy.verification.sections[index];
                    });
                    const labels = document.querySelectorAll(".field label");
                    labels.forEach((label, index) => {
                        if (copy.verification.labels[index]) label.textContent = copy.verification.labels[index];
                    });
                    const saveBtn = document.querySelector(".btn-primary");
                    if (saveBtn) saveBtn.textContent = copy.verification.save;
                    const submitBtn = document.querySelector(".btn-ghost");
                    if (submitBtn) submitBtn.textContent = copy.verification.submit;
                }

                syncLanguageUi(language);
                localStorage.setItem(languageKey, language);
            } catch (e) {
                console.error("Translation error:", e);
            }
        }

        function setupLanguageControls() {
            languageMenus.forEach((menu) => {
                menu.addEventListener("click", (event) => {
                    const item = event.target.closest("li[data-lang]");
                    if (!item) return;
                    applyLanguage(item.dataset.lang || "en");
                });
            });
        }

        function setupThemeControls() {
            themeButtons.forEach((button) => {
                button.addEventListener("click", () => {
                    const nextTheme = body.dataset.theme === "dark" ? "light" : "dark";
                    setTheme(nextTheme);
                });
            });

            if (savedTheme === "dark") {
                setTheme("dark");
            }
        }

        setupLanguageControls();
        setupThemeControls();
        applyLanguage(savedLanguage);

        window.addEventListener("storage", (event) => {
            if (event.key === languageKey) {
                applyLanguage(event.newValue || "en");
            }
            if (event.key === themeKey) {
                setTheme(event.newValue);
            }
        });
    })();
});
