import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { CircularProgress, TableFooter, TablePagination, Button } from '@mui/material';
import TableMessage from '../../../../shared/tableMessage/TableMessage';
import TablePaginationActions from '../../../../shared/pagination/TablePaginationActions';
import RowTableLogDisplay from './row-table-log-display/row-table-log-display';
import Checkbox from '@mui/material/Checkbox';
import DeleteIcon from '@mui/icons-material/Delete';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import HighlightOffIcon from '@mui/icons-material/HighlightOff';

const TableLogDisplay = ({
    articles,
    totalElements,
    isLoading,
    isFetching,
    page,
    handlePage,
    pageSize,
    handleItemSelected,
    partialCheck,
    handleDownloadAll,
    handleCleanFilter
}: any) => {
    const { t } = useTranslation();
    const [isCheckedAll, setCheckedAll] = useState(false);

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

    const handleStatusChange = (e: any, status: number) => {
        articles.map((item: any, i: number) => console.log(item.idArticle + ' checked: ' + item.checked));
    };

    const handleCheckedAll = (e: any) => {
        const checked = e.target.checked;
        setCheckedAll(checked);
        handleDownloadAll(checked);
    };

    return (
        <>
            {(isCheckedAll || partialCheck) && (
                <>
                    <Button
                        variant="contained"
                        className="table_logDisplay_button"
                        type="button"
                        startIcon={<CheckCircleOutlineIcon />}
                        onClick={(e) => handleStatusChange(e, 1)}
                    >
                        Aprobar
                    </Button>
                    <Button
                        variant="contained"
                        className="table_logDisplay_button"
                        color="error"
                        type="button"
                        startIcon={<HighlightOffIcon />}
                        onClick={(e) => handleStatusChange(e, 2)}
                    >
                        Rechazar
                    </Button>
                    <Button
                        variant="outlined"
                        className="table_logDisplay_button"
                        type="button"
                        startIcon={<DeleteIcon />}
                        onClick={(e) => handleStatusChange(e, 3)}
                    >
                        Eliminar
                    </Button>
                </>
            )}
            <TableContainer className="table__price_list" component={Paper}>
                <Table>
                    <TableBody className="table__body">
                        <TableRow className="table__log_Display_Header">
                            <TableCell>
                                <Checkbox color="default" checked={isCheckedAll} indeterminate={partialCheck} onChange={handleCheckedAll} />
                            </TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.csv')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.career')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.cau')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.modality')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.period')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.ticket')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.Shift')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.status')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.typeStudent')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.price')}</TableCell>
                        </TableRow>
                        {articles.map((item: any, i: number) => (
                            <RowTableLogDisplay key={i} item={item} handleItemSelected={handleItemSelected} handleCleanFilter={handleCleanFilter} />
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
export default TableLogDisplay;
