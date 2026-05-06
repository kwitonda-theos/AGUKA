document.addEventListener("DOMContentLoaded", () => {
    const languageKey = "page-language";
    const languageSelects = Array.from(document.querySelectorAll(".language-select"));
    const languageInitials = Array.from(document.querySelectorAll(".language-initials"));
    const languageMenuItems = Array.from(document.querySelectorAll(".language-menu li"));

    const translations = {
        en: {
            "nav-hire": "Hire engineers",
            "nav-find": "Find jobs",
            "nav-about": "About UBAKA",
            "hero-title": "Build with trusted engineers across Rwanda",
            "hero-desc": "UBAKA connects clients with verified engineers to plan, build, and manage construction projects safely and efficiently.",
            "hero-post": "Post a job offer",
            "hero-apply": "Apply for offer",
            "section-what": "What are you here to do?",
            "card-hire-title": "Hire an engineer",
            "card-hire-desc": "Post a construction job and get matched with verified professionals.",
            "card-hire-btn": "Post a job",
            "card-find-title": "Find construction jobs",
            "card-find-desc": "Apply for jobs, get hired, and earn securely, fairly.",
            "card-find-btn": "Find jobs",
            "section-login": "Log in to see your account information",
            "section-login-desc": "View past contracts, tailored suggestions, online sites, and more.",
            "section-apply": "Get or apply for offers from different clients",
            "section-apply-desc": "Make money from your own projects or build for others using UBAKA",
            "section-build": "Build your home with trusted, verified engineers",
            "section-build-desc": "Find good and trustworthy engineers with UBAKA from the comfort of your home.",
            "section-how": "How UBAKA works",
            "how-step1": "1. Post or apply for a job",
            "how-step1-desc": "Clients post projects, engineers apply or get matched.",
            "how-step2": "2. Get matched & chat",
            "how-step2-desc": "Review profiles, communicate, and agree on terms.",
            "how-step3": "3. Build with confidence",
            "how-step3-desc": "Work with verified professionals.",
            "trust-title": "Trusted by builders and homeowners",
            "trust-stat": "10+ Verified engineers",
            "footer-company": "Company",
            "footer-partners": "Partners",
            "footer-policy": "Policy"
        },
        fr: {
            "nav-hire": "Engager des ingénieurs",
            "nav-find": "Trouver des emplois",
            "nav-about": "À propos d'UBAKA",
            "hero-title": "Construisez avec des ingénieurs de confiance à travers le Rwanda",
            "hero-desc": "UBAKA connecte les clients avec des ingénieurs vérifiés pour planifier, construire et gérer des projets de construction en toute sécurité et efficacité.",
            "hero-post": "Publier une offre d'emploi",
            "hero-apply": "Postuler à une offre",
            "section-what": "Que venez-vous faire?",
            "card-hire-title": "Engager un ingénieur",
            "card-hire-desc": "Publiez un travail de construction et trouvez des professionnels vérifiés.",
            "card-hire-btn": "Publier un emploi",
            "card-find-title": "Trouver des emplois de construction",
            "card-find-desc": "Postulez à des emplois, soyez engagé et gagnez en toute sécurité.",
            "card-find-btn": "Trouver des emplois",
            "section-login": "Connectez-vous pour voir les informations de votre compte",
            "section-login-desc": "Afficher les contrats passés, les suggestions adaptées, les sites en ligne, et plus.",
            "section-apply": "Obtenir ou postuler à des offres de différents clients",
            "section-apply-desc": "Gagnez de l'argent à partir de vos propres projets ou construisez pour d'autres en utilisant UBAKA",
            "section-build": "Construisez votre maison avec des ingénieurs vérifiés et de confiance",
            "section-build-desc": "Trouvez de bons et dignes de confiance ingénieurs avec UBAKA du confort de votre maison.",
            "section-how": "Comment fonctionne UBAKA",
            "how-step1": "1. Publiez ou postulez à un emploi",
            "how-step1-desc": "Les clients publient des projets, les ingénieurs postulent ou sont appariés.",
            "how-step2": "2. Être jumelé et discuter",
            "how-step2-desc": "Examinez les profils, communiquez et convenus des conditions.",
            "how-step3": "3. Construisez en confiance",
            "how-step3-desc": "Travaillez avec des professionnels vérifiés.",
            "trust-title": "Fait confiance par les constructeurs et propriétaires",
            "trust-stat": "10+ Ingénieurs vérifiés",
            "footer-company": "Entreprise",
            "footer-partners": "Partenaires",
            "footer-policy": "Politique"
        },
        rw: {
            "nav-hire": "Yoherereza inzira",
            "nav-find": "Gushaka inzira",
            "nav-about": "Ibyerekeye UBAKA",
            "hero-title": "Kubaka hamwe n'inzira z'imiterere ikerekeza mu Rwanda",
            "hero-desc": "UBAKA ihuza ababigizi n'inzira z'imiterere zizuranye kugira ngo gushyira mu marito, kubaka, no kurongora porojetsi z'ubwubatsi neza.",
            "hero-post": "Shyiramo inzira y'akazi",
            "hero-apply": "Subiramo ku nzira",
            "section-what": "Kuvuza iki?",
            "card-hire-title": "Yoherereza inzira",
            "card-hire-desc": "Shyiramo akazi k'ubwubatsi kandi wonane n'abakozi ba seriveri.",
            "card-hire-btn": "Shyiramo akazi",
            "card-find-title": "Gushaka akazi k'ubwubatsi",
            "card-find-desc": "Subiramo ku nzira, habwa akazi, kandi wite neza.",
            "card-find-btn": "Gushaka akazi",
            "section-login": "Injira mugihe cyo kubona amakuru y'akawu k'akawe",
            "section-login-desc": "Reba amasezerano ashize, inama zisuzuye, ururiniarugambage, n'ibindi.",
            "section-apply": "Wabona cyangwa subiramo ku nzira z'abakozi batandukanye",
            "section-apply-desc": "Wite mu porojetsi z'akawe cyangwa kubaka kuri izindi hakoresheje UBAKA",
            "section-build": "Kubaka inzu y'akawe hamwe n'inzira z'imiterere ikerekeza",
            "section-build-desc": "Gushaka inzira nziza kandi ikerekeza hakoresheje UBAKA mucyuma cy'akawe.",
            "section-how": "Uko UBAKA ikorera",
            "how-step1": "1. Shyiramo cyangwa subiramo ku nzira",
            "how-step1-desc": "Ababigizi bashyira porojetsi, inzira suramo cyangwa hahuranijwe.",
            "how-step2": "2. Hahuranijwe no kuvugisha",
            "how-step2-desc": "Reba profile, kuvugisha, kandi kuganira ku bijyanye.",
            "how-step3": "3. Kubaka naha",
            "how-step3-desc": "Kugira inyungu n'abakozi ba seriveri.",
            "trust-title": "Bikerekeza abakozi no miryango",
            "trust-stat": "10+ Inzira zizuranye",
            "footer-company": "Isosiyete",
            "footer-partners": "Inzira",
            "footer-policy": "Amahitamo"
        }
    };

    function applyLanguage(lang) {
        // Update language initials
        languageInitials.forEach(el => {
            el.textContent = lang.toUpperCase();
        });

        // Update all elements with data-i18n attribute
        document.querySelectorAll("[data-i18n]").forEach(el => {
            const key = el.getAttribute("data-i18n");
            if (translations[lang] && translations[lang][key]) {
                el.textContent = translations[lang][key];
            }
        });

        // Update active menu item
        languageMenuItems.forEach(item => {
            item.classList.remove("active");
        });
        const activeItem = document.querySelector(`.language-menu li[data-lang="${lang}"]`);
        if (activeItem) activeItem.classList.add("active");

        // Save language preference
        localStorage.setItem(languageKey, lang);

        // Update page language attribute
        document.documentElement.lang = lang;
    }

    // Load saved language or default to 'en'
    const savedLanguage = localStorage.getItem(languageKey) || "en";
    applyLanguage(savedLanguage);

    // Handle language select dropdown
    languageSelects.forEach(select => {
        select.addEventListener("change", (e) => {
            applyLanguage(e.target.value);
        });
    });

    // Handle language menu items
    languageMenuItems.forEach(item => {
        item.addEventListener("click", () => {
            const lang = item.getAttribute("data-lang");
            applyLanguage(lang);
            // Update select value
            languageSelects.forEach(select => {
                select.value = lang;
            });
        });
    });
});
