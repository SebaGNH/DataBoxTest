import { fireEvent, render, waitFor } from '@testing-library/react';
import RowTableCurrentPrices from './row-table-current-prices';

describe('<RowTableCurrentPrices />', () => {
    it('El modulo se renderiza correctamente', async () => {
        const item = {
            turnoCursadoDesc: 'TARDE',
            cau: 'SENIOR'
        };

        const { getByText, container } = render(<RowTableCurrentPrices item={item}></RowTableCurrentPrices>);

        fireEvent(
            container.querySelectorAll('#rmt-courseExpand')[0],
            new MouseEvent('click', {
                bubbles: true,
                cancelable: true
            })
        );
        await waitFor(() => getByText('TARDE'));
        await waitFor(() => getByText('SENIOR'));
    });
});
