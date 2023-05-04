import { render, RenderOptions, waitFor } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { QueryClient, QueryClientProvider } from 'react-query';
import CurrentPrices from './current-prices';
import { BrowserRouter as Router } from 'react-router-dom';
import { AppContext } from '../../../App.context';
import { STATE_LOGIN } from '../../../utils/constants';
import { currentPricesService } from '../../../services/current-prices.service';

jest.mock('../../../services/current-prices.service', () => ({
    currentPricesService: {
        getArticlesCurrentPrices: jest.fn()
    }
}));

jest.mock('@mui/x-date-pickers/internals/demo', () => ({
    __esModule: true,
    default: jest.fn(),
    DemoContainer: jest.fn(),
    DatePicker: jest.fn()
}));

describe('<CurrentPrices />', () => {
    it('El modulo se renderiza correctamente', async () => {
        jest.mock('axios');

        const mockGetPrices = jest.spyOn(currentPricesService, 'getArticlesCurrentPrices').mockImplementation(() =>
            Promise.resolve({
                totalElements: 1,
                articles: [
                    {
                        cau: 'SENIOR',
                        idCau: 1062,
                        modality: 'D',
                        typeTicket: 'II',
                        rubro: 'AX',
                        csvId: 6066,
                        descripcionCarrera: 'Procurador',
                        turnoCursadoDesc: 'SÁBADO',
                        periodoAcademico: '1/23',
                        tipoArancel: 'Ticket Graduacion',
                        tipoALumno: 'RI',
                        precio: 27000,
                        fechaInicio: '2023-02-27T13:24:57.000+00:00',
                        fechaCarga: '2023-02-27T13:24:57.000+00:00',
                        tipoModalidad: 'DISTANCIA - ED HOME'
                    }
                ]
            })
        );

        const dispatch = jest.fn();
        let renderOptions: RenderOptions;
        await act(async () => {
            renderOptions = render(
                <QueryClientProvider client={new QueryClient()}>
                    <Router>
                        <AppContext.Provider value={{ state: STATE_LOGIN, dispatch }}>
                            <CurrentPrices></CurrentPrices>
                        </AppContext.Provider>
                    </Router>
                </QueryClientProvider>
            );
        });
        await waitFor(() => {
            expect(mockGetPrices).toBeCalled();
        });
        await waitFor(() => {
            expect(renderOptions.container).toHaveTextContent('titles.currenPrice');
        });
    });
});
