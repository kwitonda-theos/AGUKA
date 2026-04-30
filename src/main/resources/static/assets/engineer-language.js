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

    const dashboard = {
        title: "Engineer Dashboard - AGUKA",
        nav: ["Dashboard", "Browse jobs", "Projects", "Notifications"],
        profile: "Profile",
        welcome: "Welcome back, Engineer's name",
        subtitle: "Here's what's happening with your account",
        stats: ["Profile status", "Active applications", "Ratings"],
        values: ["verified", "10", "4/5"],
        section: "Active jobs",
        job1: {
            name: "Kigali Smart Grid Expansion",
            desc: "Structural audit and schematic reinforcement for phase 2 of the urban electrification project. Requires immediate onsite verification.",
            primary: "Review specs",
            secondary: "Decline",
        },
        job2: {
            name: "Heritier's House",
            desc: "Modern sub-urban house for heritier",
            primary: "Review specs",
            secondary: "Decline",
        },
    };

    const projects = {
        title: "Engineer Projects - AGUKA",
        nav: ["Dashboard", "Browse jobs", "Projects", "Notifications"],
        profile: "Profile",
        heading: "Browse jobs",
        subtitle: "Find and search for posted jobs tailored to you.",
        filters: ["Location", "Budget", "Specialization"],
        locations: ["kicukiro", "nyarugenge", "gasabo"],
        budgets: ["10,000,000 - 20,000,000", "20,000,000 - 50,000,000", "50,000,000+"],
        specializations: ["civil engineer", "architect", "structural engineer"],
        section: "jobs",
        job1: {
            name: "Kigali Smart Grid Expansion",
            meta: ["kicukiro, Kigali", "KIGALI group"],
            desc: "Structural audit and schematic reinforcement for phase 2 of the urban electrification project. Requires immediate onsite verification.",
            primary: "Apply now",
            secondary: "Remove",
        },
        job2: {
            name: "Heritier's House",
            meta: ["nyarugenge, norvege", "heritier"],
            desc: "Modern sub-urban house for heritier",
            primary: "Apply now",
            secondary: "Remove",
        },
    };

    const notifications = {
        title: "Engineer Notifications - AGUKA",
        nav: ["Dashboard", "Browse jobs", "Projects", "Notifications"],
        profile: "Profile",
        heading: "Notifications",
        subtitle: "See who contacted you or responses from the jobs you applied to.",
        notice1: {
            headBefore: "approved you in",
            headAfter: "project",
            body: "Kindly look at your whatsapp to see further details",
        },
        notice2: {
            headBefore: "completed project",
            headAfter: "project",
            body: "You approved the completion of this project",
        },
        time: "just now",
        deleteLabel: "Delete notification",
    };

    const verification = {
        title: "Engineer Verification - AGUKA",
        heading: "Engineer Profile",
        subtitle: "Complete your profile to get matched with construction jobs.",
        status: "verified / under preview / unverified",
        sections: [
            "Personal information",
            "Professional information",
            "Work preferences",
            "Verification",
            "Professional Documents",
            "Profile photo",
            "Ratings & reviews",
        ],
        labels: [
            "Name",
            "phone",
            "Email",
            "Location",
            "Profession",
            "years of experience",
            "skills",
            "short bio",
            "available for work?",
            "willing to travel",
            "National ID number",
            "upload national ID",
            "certificates",
            "upload picture",
        ],
        placeholders: [
            "your name (read-only)",
            "your phone",
            "your email (read-only)",
            "your location",
            "your profession (drop down)",
            "number drop down",
            "multiple inputs",
            "Briefly describe your experience and the type of projects you worked on.",
            "yes / no",
            "with in district / nationwide",
            "enter id number",
        ],
        options: {
            profession: ["civil engineer", "architect", "project manager", "quantity surveyor"],
            experience: ["1 - 2 years", "3 - 5 years", "6 - 9 years", "10+ years"],
            available: ["yes", "no"],
            travel: ["within district", "nationwide"],
        },
        verified: "Verified engineers get more job opportunities and higher trust from clients.",
        documentNote: "Uploading certificates improves your chances of being hired.",
        photoNote: "A clear photo helps clients trust and recognize you.",
        ratingLabel: "Rating 5 out of 5",
        save: "Save",
        hint: "Note: submit for verification after making sure that all the required documents are available",
        submit: "submit for verification",
    };

    function setSelectOptions(select, options) {
        if (!select) {
            return;
        }
        Array.from(select.options).forEach((option, index) => {
            option.textContent = options[index] || option.textContent;
        });
    }

    function setTheme(theme) {
        if (theme === "dark") {
            body.dataset.theme = "dark";
        } else {
            delete body.dataset.theme;
        }
        localStorage.setItem(themeKey, theme);
    }

    function syncLanguageUi(language) {
        languageSelects.forEach((select) => {
            if (select.value !== language) {
                select.value = language;
            }
        });

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

    function applyDashboard(language) {
        const copy = { en: dashboard, fr: {
            ...dashboard,
            title: "Tableau de bord de l'ingénieur - AGUKA",
            nav: ["Tableau de bord", "Parcourir les offres", "Projets", "Notifications"],
            profile: "Profil",
            welcome: "Bon retour, nom de l'ingénieur",
            subtitle: "Voici ce qui se passe sur votre compte",
            stats: ["Statut du profil", "Candidatures actives", "Notes"],
            values: ["vérifié", "10", "4/5"],
            section: "Emplois actifs",
            job1: {
                name: "Extension du réseau intelligent de Kigali",
                desc: "Audit structurel et renforcement des plans pour la phase 2 du projet d'électrification urbaine. Vérification sur site immédiate requise.",
                primary: "Examiner les détails",
                secondary: "Refuser",
            },
            job2: {
                name: "Maison d'Heritier",
                desc: "Maison moderne de banlieue pour Heritier",
                primary: "Examiner les détails",
                secondary: "Refuser",
            },
        }, rw: {
            ...dashboard,
            title: "Ikibaho cy'umuhanga - AGUKA",
            nav: ["Ahabanza", "Sura imirimo", "Imishinga", "Amatangazo"],
            profile: "Umwirondoro",
            welcome: "Mwakire neza, izina ry'umuhanga",
            subtitle: "Dore ibibera kuri konte yawe",
            stats: ["Imiterere ya konte", "Ubusabe buhari", "Amanota"],
            values: ["byemejwe", "10", "4/5"],
            section: "Imirimo iriho",
            job1: {
                name: "Kwagura urusobe rw'amashanyarazi rwa Kigali",
                desc: "Isuzuma ry'imiterere n'ubushimangira bw'ibishushanyo ku cyiciro cya 2 cy'umushinga wo kumurika umujyi. Harasabwa kugenzura ahakorerwa ako kanya.",
                primary: "Reba ibisobanuro",
                secondary: "Banza",
            },
            job2: {
                name: "Inzu ya Heritier",
                desc: "Inzu ya kijyambere yo mu nkengero za Heritier",
                primary: "Reba ibisobanuro",
                secondary: "Banza",
            },
        } }[language] || dashboard;

        const navLabels = document.querySelectorAll(".nav-item span");
        const statTitles = document.querySelectorAll(".stat h3");
        const statValues = document.querySelectorAll(".stat p");
        const jobNames = document.querySelectorAll(".job-name");
        const jobDescs = document.querySelectorAll(".job-desc");
        const jobButtons = document.querySelectorAll(".job-actions .btn");
        const profileLabel = document.querySelector(".profile-cta span");
        const welcome = document.querySelector(".welcome");
        const subtitle = document.querySelector(".sub");
        const section = document.querySelector(".section-title");

        document.documentElement.lang = language;
        document.title = copy.title;
        navLabels.forEach((label, index) => (label.textContent = copy.nav[index]));
        if (profileLabel) profileLabel.textContent = copy.profile;
        if (welcome) welcome.textContent = copy.welcome;
        if (subtitle) subtitle.textContent = copy.subtitle;
        statTitles.forEach((title, index) => (title.textContent = copy.stats[index]));
        statValues.forEach((value, index) => (value.textContent = copy.values[index]));
        if (section) section.textContent = copy.section;
        if (jobNames[0]) jobNames[0].textContent = copy.job1.name;
        if (jobDescs[0]) jobDescs[0].textContent = copy.job1.desc;
        if (jobButtons[0]) jobButtons[0].textContent = copy.job1.primary;
        if (jobButtons[1]) jobButtons[1].textContent = copy.job1.secondary;
        if (jobNames[1]) jobNames[1].textContent = copy.job2.name;
        if (jobDescs[1]) jobDescs[1].textContent = copy.job2.desc;
        if (jobButtons[2]) jobButtons[2].textContent = copy.job2.primary;
        if (jobButtons[3]) jobButtons[3].textContent = copy.job2.secondary;
    }

    function applyProjects(language) {
        const copy = { en: projects, fr: {
            ...projects,
            title: "Projets de l'ingénieur - AGUKA",
            nav: ["Tableau de bord", "Parcourir les offres", "Projets", "Notifications"],
            profile: "Profil",
            heading: "Parcourir les offres",
            subtitle: "Trouvez et recherchez des emplois publiés qui vous correspondent.",
            filters: ["Lieu", "Budget", "Spécialisation"],
            locations: ["kicukiro", "nyarugenge", "gasabo"],
            budgets: ["10 000 000 - 20 000 000", "20 000 000 - 50 000 000", "50 000 000+"],
            specializations: ["ingénieur civil", "architecte", "ingénieur structure"],
            section: "emplois",
            job1: {
                name: "Extension du réseau intelligent de Kigali",
                meta: ["kicukiro, Kigali", "groupe KIGALI"],
                desc: "Audit structurel et renforcement des plans pour la phase 2 du projet d'électrification urbaine. Vérification sur site immédiate requise.",
                primary: "Postuler maintenant",
                secondary: "Retirer",
            },
            job2: {
                name: "Maison d'Heritier",
                meta: ["nyarugenge, Norvège", "Heritier"],
                desc: "Maison moderne de banlieue pour Heritier",
                primary: "Postuler maintenant",
                secondary: "Retirer",
            },
        }, rw: {
            ...projects,
            title: "Imishinga y'umuhanga - AGUKA",
            nav: ["Ahabanza", "Sura imirimo", "Imishinga", "Amatangazo"],
            profile: "Umwirondoro",
            heading: "Sura imirimo",
            subtitle: "Shaka kandi ushakishe imirimo yashyizweho ihuye nawe.",
            filters: ["Aho iri", "Ingengo y'imari", "Ubuhezanguni"],
            locations: ["kicukiro", "nyarugenge", "gasabo"],
            budgets: ["10.000.000 - 20.000.000", "20.000.000 - 50.000.000", "50.000.000+"],
            specializations: ["umuhanga mu bwubatsi", "umwubatsi", "umuhanga mu miterere"],
            section: "imirimo",
            job1: {
                name: "Kwagura urusobe rw'amashanyarazi rwa Kigali",
                meta: ["kicukiro, Kigali", "Itsinda KIGALI"],
                desc: "Isuzuma ry'imiterere n'ubushimangira bw'ibishushanyo ku cyiciro cya 2 cy'umushinga wo kumurika umujyi. Harasabwa kugenzura aho hakoreshwa ako kanya.",
                primary: "Saba ubu",
                secondary: "Kuraho",
            },
            job2: {
                name: "Inzu ya Heritier",
                meta: ["nyarugenge, Noruveji", "Heritier"],
                desc: "Inzu ya kijyambere yo mu nkengero za Heritier",
                primary: "Saba ubu",
                secondary: "Kuraho",
            },
        } }[language] || projects;

        const navLabels = document.querySelectorAll(".nav-item span");
        const profileLabel = document.querySelector(".profile-cta span");
        const heading = document.querySelector(".title");
        const subtitle = document.querySelector(".subtitle");
        const filterLabels = document.querySelectorAll(".filter label");
        const locationSelect = document.getElementById("location");
        const budgetSelect = document.getElementById("budget");
        const specializationSelect = document.getElementById("specialization");
        const section = document.querySelector(".section-title");
        const jobCards = document.querySelectorAll(".job-card");

        document.documentElement.lang = language;
        document.title = copy.title;
        navLabels.forEach((label, index) => (label.textContent = copy.nav[index]));
        if (profileLabel) profileLabel.textContent = copy.profile;
        if (heading) heading.textContent = copy.heading;
        if (subtitle) subtitle.textContent = copy.subtitle;
        filterLabels.forEach((label, index) => (label.textContent = copy.filters[index]));
        setSelectOptions(locationSelect, copy.locations);
        setSelectOptions(budgetSelect, copy.budgets);
        setSelectOptions(specializationSelect, copy.specializations);
        if (section) section.textContent = copy.section;

        if (jobCards[0]) {
            jobCards[0].querySelector(".job-name").textContent = copy.job1.name;
            jobCards[0].querySelector(".meta").children[0].textContent = copy.job1.meta[0];
            jobCards[0].querySelector(".meta").children[1].textContent = copy.job1.meta[1];
            jobCards[0].querySelector(".job-desc").textContent = copy.job1.desc;
            jobCards[0].querySelectorAll(".btn")[0].textContent = copy.job1.primary;
            jobCards[0].querySelectorAll(".btn")[1].textContent = copy.job1.secondary;
        }
        if (jobCards[1]) {
            jobCards[1].querySelector(".job-name").textContent = copy.job2.name;
            jobCards[1].querySelector(".meta").children[0].textContent = copy.job2.meta[0];
            jobCards[1].querySelector(".meta").children[1].textContent = copy.job2.meta[1];
            jobCards[1].querySelector(".job-desc").textContent = copy.job2.desc;
            jobCards[1].querySelectorAll(".btn")[0].textContent = copy.job2.primary;
            jobCards[1].querySelectorAll(".btn")[1].textContent = copy.job2.secondary;
        }
    }

    function applyNotifications(language) {
        const copy = { en: notifications, fr: {
            ...notifications,
            title: "Notifications de l'ingénieur - AGUKA",
            nav: ["Tableau de bord", "Parcourir les offres", "Projets", "Notifications"],
            profile: "Profil",
            heading: "Notifications",
            subtitle: "Voyez qui vous a contacté ou les réponses aux emplois auxquels vous avez postulé.",
            notice1: {
                headBefore: "vous a approuvé dans le",
                headAfter: "projet",
                body: "Veuillez consulter votre WhatsApp pour plus de détails",
            },
            notice2: {
                headBefore: "a terminé le",
                headAfter: "projet",
                body: "Vous avez approuvé la fin de ce projet",
            },
            time: "à l'instant",
            deleteLabel: "Supprimer la notification",
        }, rw: {
            ...notifications,
            title: "Amatangazo y'umuhanga - AGUKA",
            nav: ["Ahabanza", "Sura imirimo", "Imishinga", "Amatangazo"],
            profile: "Umwirondoro",
            heading: "Amatangazo",
            subtitle: "Reba uwakuvugishije cyangwa ibisubizo by'imirimo wasabye.",
            notice1: {
                headBefore: "yaguhisemo mu",
                headAfter: "mushinga",
                body: "Nyamuneka reba kuri WhatsApp yawe kugira ngo ubone ibisobanuro birambuye",
            },
            notice2: {
                headBefore: "yarangije umushinga",
                headAfter: "mushinga",
                body: "Wemeje kurangiza k'uyu mushinga",
            },
            time: "ubungubu",
            deleteLabel: "Siba itangazo",
        } }[language] || notifications;

        const navLabels = document.querySelectorAll(".nav-item span");
        const profileLabel = document.querySelector(".profile-cta span");
        const heading = document.querySelector(".title");
        const subtitle = document.querySelector(".subtitle");
        const noticeHeads = document.querySelectorAll(".notice-head");
        const noticeBodies = document.querySelectorAll(".notice-body");
        const times = document.querySelectorAll(".time");
        const deleteButtons = document.querySelectorAll(".trash");

        document.documentElement.lang = language;
        document.title = copy.title;
        navLabels.forEach((label, index) => (label.textContent = copy.nav[index]));
        if (profileLabel) profileLabel.textContent = copy.profile;
        if (heading) heading.textContent = copy.heading;
        if (subtitle) subtitle.textContent = copy.subtitle;
        if (noticeHeads[0]) {
            noticeHeads[0].innerHTML = `<span class="name">ALEX</span> ${copy.notice1.headBefore} <span class="project">GLOBAL CONSTRUCTION ${copy.notice1.headAfter}</span>`;
        }
        if (noticeBodies[0]) noticeBodies[0].textContent = copy.notice1.body;
        if (noticeHeads[1]) {
            noticeHeads[1].innerHTML = `<span class="name" style="text-transform:none;">Heritier</span> ${copy.notice2.headBefore} <span class="project">kigali construction ${copy.notice2.headAfter}</span>`;
        }
        if (noticeBodies[1]) noticeBodies[1].textContent = copy.notice2.body;
        times.forEach((time) => (time.textContent = copy.time));
        deleteButtons.forEach((button) => button.setAttribute("aria-label", copy.deleteLabel));
    }

    function applyVerification(language) {
        const copy = { en: verification, fr: {
            ...verification,
            title: "Vérification de l'ingénieur - AGUKA",
            heading: "Profil de l'ingénieur",
            subtitle: "Complétez votre profil pour être mis en relation avec des emplois de construction.",
            status: "vérifié / en aperçu / non vérifié",
            sections: [
                "Informations personnelles",
                "Informations professionnelles",
                "Préférences de travail",
                "Vérification",
                "Documents professionnels",
                "Photo de profil",
                "Notes et avis",
            ],
            labels: [
                "Nom",
                "téléphone",
                "E-mail",
                "Lieu",
                "Profession",
                "années d'expérience",
                "compétences",
                "courte biographie",
                "disponible pour travailler ?",
                "prêt à voyager",
                "Numéro de carte d'identité",
                "téléverser la carte d'identité",
                "certificats",
                "téléverser une photo",
            ],
            placeholders: [
                "votre nom (lecture seule)",
                "votre téléphone",
                "votre e-mail (lecture seule)",
                "votre lieu",
                "votre profession (liste déroulante)",
                "liste déroulante de nombres",
                "plusieurs saisies",
                "Décrivez brièvement votre expérience et le type de projets sur lesquels vous avez travaillé.",
                "oui / non",
                "dans le district / à l'échelle nationale",
                "saisissez le numéro d'identité",
            ],
            options: {
                profession: ["ingénieur civil", "architecte", "chef de projet", "géomètre"],
                experience: ["1 - 2 ans", "3 - 5 ans", "6 - 9 ans", "10 ans et plus"],
                available: ["oui", "non"],
                travel: ["dans le district", "à l'échelle nationale"],
            },
            verified: "Les ingénieurs vérifiés obtiennent plus d'opportunités et gagnent davantage la confiance des clients.",
            documentNote: "Le téléchargement de certificats améliore vos chances d'être embauché.",
            photoNote: "Une photo claire aide les clients à vous faire confiance et à vous reconnaître.",
            ratingLabel: "Note 5 sur 5",
            save: "Enregistrer",
            hint: "Remarque : soumettez pour vérification après vous être assuré que tous les documents requis sont disponibles",
            submit: "soumettre pour vérification",
        }, rw: {
            ...verification,
            title: "Igenzura ry'umuhanga - AGUKA",
            heading: "Umwirondoro w'umuhanga",
            subtitle: "Uzuza umwirondoro wawe kugira ngo uhuze n'imirimo y'ubwubatsi.",
            status: "byemejwe / biri gusuzumwa / bitaremezwa",
            sections: [
                "Amakuru yihariye",
                "Amakuru y'umwuga",
                "Ibyifuzo by'akazi",
                "Igenzura",
                "Inyandiko z'umwuga",
                "Ifoto y'umwirondoro",
                "Amanota n'ibitekerezo",
            ],
            labels: [
                "Izina",
                "telefone",
                "Imeyili",
                "Aho ubarizwa",
                "Umwuga",
                "imyaka y'uburambe",
                "ubuhanga",
                "amakuru magufi",
                "uraboneka ku kazi?",
                "witeguye kugenda kure",
                "Nimero y'irangamuntu",
                "ohereza irangamuntu",
                "ibyemezo",
                "ohereza ifoto",
            ],
            placeholders: [
                "izina ryawe (ntirishobora guhindurwa)",
                "telefone yawe",
                "imeyili yawe (ntirishobora guhindurwa)",
                "aho ubarizwa",
                "umwuga wawe (urutonde)",
                "urutonde rw'imibare",
                "injiza byinshi",
                "Sobanura muri make uburambe bwawe n'ubwoko bw'imishinga wakoranyeho.",
                "yego / oya",
                "mu karere / igihugu cyose",
                "injiza nimero y'irangamuntu",
            ],
            options: {
                profession: ["umuhanga mu bwubatsi", "umwubatsi", "umuyobozi w'umushinga", "umupimyi"],
                experience: ["imyaka 1 - 2", "imyaka 3 - 5", "imyaka 6 - 9", "imyaka 10+"],
                available: ["yego", "oya"],
                travel: ["mu karere", "mu gihugu hose"],
            },
            verified: "Abahanga bemejwe bahabwa amahirwe menshi y'akazi kandi bakizwaho icyizere n'abakiriya.",
            documentNote: "Gushyira ho ibyemezo byongera amahirwe yawe yo guhabwa akazi.",
            photoNote: "Ifoto isobanutse ifasha abakiriya kukwizera no kukumenya neza.",
            ratingLabel: "Amanota 5 kuri 5",
            save: "Bika",
            hint: "Icyitonderwa: ohereza kugira ngo igenzurwe nyuma yo kwemeza ko inyandiko zose zisabwa ziriho",
            submit: "ohereza kugira ngo igenzurwe",
        } }[language] || verification;

        const heading = document.querySelector(".hero h1");
        const subtitle = document.querySelector(".hero p");
        const status = document.querySelector(".status");
        const sectionTitles = document.querySelectorAll(".section-title");
        const sectionNotes = document.querySelectorAll(".section-note");
        const labels = document.querySelectorAll(".field label");
        const inputs = document.querySelectorAll("input, textarea");
        const selects = document.querySelectorAll("select");
        const stars = document.querySelector(".stars");
        const buttons = document.querySelectorAll("button");
        const hint = document.querySelector(".hint");

        document.documentElement.lang = language;
        document.title = copy.title;
        if (heading) heading.textContent = copy.heading;
        if (subtitle) subtitle.textContent = copy.subtitle;
        if (status) status.textContent = copy.status;
        sectionTitles.forEach((title, index) => (title.textContent = copy.sections[index]));
        if (sectionNotes[0]) sectionNotes[0].textContent = copy.verified;
        if (sectionNotes[1]) sectionNotes[1].textContent = copy.documentNote;
        if (sectionNotes[2]) sectionNotes[2].textContent = copy.photoNote;
        labels.forEach((label, index) => (label.textContent = copy.labels[index]));

        if (inputs[0]) inputs[0].placeholder = copy.placeholders[0];
        if (inputs[1]) inputs[1].placeholder = copy.placeholders[1];
        if (inputs[2]) inputs[2].placeholder = copy.placeholders[2];
        if (inputs[3]) inputs[3].placeholder = copy.placeholders[3];
        if (selects[0]) selects[0].options[0].textContent = copy.placeholders[4];
        if (selects[1]) selects[1].options[0].textContent = copy.placeholders[5];
        if (inputs[4]) inputs[4].placeholder = copy.placeholders[6];
        if (inputs[5]) inputs[5].placeholder = copy.placeholders[7];
        if (selects[2]) selects[2].options[0].textContent = copy.placeholders[8];
        if (selects[3]) selects[3].options[0].textContent = copy.placeholders[9];
        if (inputs[6]) inputs[6].placeholder = copy.placeholders[10];

        setSelectOptions(selects[0], [copy.placeholders[4], ...copy.options.profession]);
        setSelectOptions(selects[1], [copy.placeholders[5], ...copy.options.experience]);
        setSelectOptions(selects[2], [copy.placeholders[8], ...copy.options.available]);
        setSelectOptions(selects[3], [copy.placeholders[9], ...copy.options.travel]);

        if (stars) stars.setAttribute("aria-label", copy.ratingLabel);
        if (buttons[0]) buttons[0].textContent = copy.save;
        if (hint) hint.textContent = copy.hint;
        if (buttons[1]) buttons[1].textContent = copy.submit;
    }

    function applyLanguage(language) {
        syncLanguageUi(language);
        if (document.querySelector(".welcome")) {
            applyDashboard(language);
        } else if (document.querySelector(".filter")) {
            applyProjects(language);
        } else if (document.querySelector(".notice")) {
            applyNotifications(language);
        } else if (document.querySelector(".hero")) {
            applyVerification(language);
        }
        localStorage.setItem(languageKey, language);
    }

    function setupLanguageControls() {
        languageSelects.forEach((select) => {
            select.addEventListener("change", () => {
                applyLanguage(select.value || "en");
            });
        });

        languageMenus.forEach((menu) => {
            menu.addEventListener("click", (event) => {
                const item = event.target.closest("li[data-lang]");
                if (!item) {
                    return;
                }
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

    function syncFromStorage() {
        const currentLanguage = localStorage.getItem(languageKey) || "en";
        syncLanguageUi(currentLanguage);
        applyLanguage(currentLanguage);
    }

    setupLanguageControls();
    setupThemeControls();
    syncFromStorage();

    window.addEventListener("storage", (event) => {
        if (event.key === languageKey || event.key === themeKey) {
            syncFromStorage();
            if (event.key === themeKey && event.newValue !== "dark") {
                setTheme("light");
            }
        }
    });
})();
