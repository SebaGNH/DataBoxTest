import React, { useRef } from 'react';
import { useTranslation } from 'react-i18next';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { CircularProgress, TableFooter, TablePagination, Button } from '@mui/material';
import TableMessage from '../../../shared/tableMessage/TableMessage';
import TablePaginationActions from '../../../shared/pagination/TablePaginationActions';
import RowTableCurrentPrices from './row-table-current-prices/row-table-current-prices';
import Checkbox from '@mui/material/Checkbox';
import { Download } from '@mui/icons-material';
import { CSVLink } from 'react-csv';

const TableCurrentPrices = ({
    articles,
    totalElements,
    isLoading,
    isFetching,
    page,
    handlePage,
    pageSize,
    handleItemSelected,
    partialCheck,
    isCheckedAll,
    handleCheckedAll,
    articlesSelecteds
}: any) => {
    const { t } = useTranslation();
    const refs = useRef<any | null>(null);

    const headers = [
        { label: 'CARRERA', key: 'idCarrera' },
        { label: 'MODALIDAD', key: 'modality' },
        { label: 'TIPO_MODALIDAD', key: 'idTipoModalidad' },
        { label: 'TIPO_DE_TICKET', key: 'typeTicket' },
        { label: 'TIPO_ARANCEL', key: 'tipoArancelId' },
        { label: 'PORCENTAJE', key: 'porcentaje' },
        { label: 'TURNO', key: 'turno' },
        { label: 'TURNO_CURSADO_CARRERA', key: 'turnoCursado' },
        { label: 'CAU', key: 'cau' },
        { label: 'PRECIO', key: 'precio' },
        { label: 'PERIODO_ACADEMICO', key: 'periodoAcademico' },
        { label: 'MONEDA', key: 'moneda' },
        { label: 'CANTIDAD_DE_MATERIAS', key: 'cantidadMaterias' },
        { label: 'TIPO_DE_ALUMNO', key: 'tipoALumno' },
        { label: 'FECHA_DE_INICIO', key: 'fechaInicio' }
    ];

    if (isLoading || isFetching) {
        return (
            <div className="flex-justify-content_center">
                <CircularProgress id="rmt-loadingPrograms" color="success" />
            </div>
        );
    }
    if (articles?.length === 0) return <TableMessage msg={t('messages.resultsNotFount')} />;

    const handleChangePage = (event: React.MouseEvent<HTMLButtonElement> | null, currenPage: number) => {
        handlePage(currenPage);
    };

    const handleDownloadCSV = () => {
        refs.current.link.click();
    };

    return (
        <>
            {(isCheckedAll || partialCheck) && (
                <>
                    <Button variant="contained" type="button" startIcon={<Download />} onClick={handleDownloadCSV}>
                        Descargar
                    </Button>
                    <CSVLink
                        data={articlesSelecteds.filter((item: any) => item.checked)}
                        headers={headers}
                        filename={'lista_precios.csv'}
                        ref={refs}
                        separator={';'}
                    />{' '}
                </>
            )}
            <TableContainer className="table__price_list" component={Paper}>
                <Table>
                    <TableBody className="table__body">
                        <TableRow className="table__courseProgramHeadArticles">
                            <TableCell>
                                <Checkbox color="default" checked={isCheckedAll} indeterminate={partialCheck} onChange={(e) => handleCheckedAll(e)} />
                            </TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.csv')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.career')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.cau')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.modality')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.period')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.tariff')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.ticket')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.Shift')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.typeStudent')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.price')}</TableCell>
                        </TableRow>
                        {articles.map((item: any, i: number) => (
                            <RowTableCurrentPrices key={i} item={item} handleItemSelected={handleItemSelected} />
                        ))}
                    </TableBody>
                    <TableFooter>
                        <TableRow>
                            <TablePagination
                                rowsPerPageOptions={[]}
                                colSpan={2}
                                count={totalElements}
                                rowsPerPage={pageSize}
                                page={page}
                                SelectProps={{
                                    inputProps: {
                                        'aria-label': 'rows per page'
                                    },
                                    native: true
                                }}
                                onPageChange={handleChangePage}
                                ActionsComponent={TablePaginationActions}
                                labelDisplayedRows={({ from, to, count }) => {
                                    return `${from}–${to} de ${count !== -1 ? count : `más que ${to}`}`;
                                }}
                            />
                        </TableRow>
                    </TableFooter>
                </Table>
            </TableContainer>
        </>
    );
};
export default TableCurrentPrices;
