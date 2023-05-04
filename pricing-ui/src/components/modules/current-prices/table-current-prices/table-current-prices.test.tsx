import { render, waitFor } from '@testing-library/react';
import TableCurrentPrices from './table-current-prices';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter as Router } from 'react-router-dom';
import { AppContext } from '../../../../App.context';
import { STATE_LOGIN } from '../../../../utils/constants';

describe('<FilterCurrentPrices />', () => {
    it('El modulo se renderiza correctamente', async () => {
        const mockArticles = [
            { turnoCursadoDesc: 'TARDE', cau: 'SENIOR' },
            { turnoCursadoDesc: 'NOCHE', cau: 'SENIOR' }
        ];

        const { getByText } = render(
            <QueryClientProvider client={new QueryClient()}>
                <Router>
                    <AppContext.Provider value={{ state: STATE_LOGIN, dispatch: () => {} }}>
                        <TableCurrentPrices articles={mockArticles}></TableCurrentPrices>
                    </AppContext.Provider>
                </Router>
            </QueryClientProvider>
        );

        await waitFor(() => {
            const description0 = mockArticles[0].turnoCursadoDesc.toUpperCase();
            expect(getByText(description0)).toHaveTextContent(description0);
            const description1 = mockArticles[1].turnoCursadoDesc.toUpperCase();
            expect(getByText(description1)).toHaveTextContent(description1);
        });
    });

    it('Loading state', async () => {
        const dispatch = jest.fn();
        const { container } = render(
            <Router>
                <AppContext.Provider value={{ state: STATE_LOGIN, dispatch }}>
                    <TableCurrentPrices articles={undefined} isLoading={true} handlePage={() => {}} />
                </AppContext.Provider>
            </Router>
        );
        expect(container.querySelector('#rmt-loadingPrograms')).toBeTruthy();
    });
});
