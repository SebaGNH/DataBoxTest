function formatNumber(number: number) {
    return new Intl.NumberFormat('ES-MX', {
        style: 'currency',
        currency: 'MXN'
    }).format(number);
}
export default formatNumber;
