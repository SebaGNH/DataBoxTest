export const getBaseUrl = () => {
    return `${process.env.REACT_APP_PRICING_BFF_URL || ''}`;
};