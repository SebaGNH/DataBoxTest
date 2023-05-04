import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import translationES from './es.json';
import translationEN from './en.json';

const resources = {
    es: {
        translation: translationES
    },
    en: {
        translation: translationEN
    }
};

i18n.use(initReactI18next).init({
    resources,
    lng: 'es',
    interpolation: {
        escapeValue: false
    }
});

export default i18n;
