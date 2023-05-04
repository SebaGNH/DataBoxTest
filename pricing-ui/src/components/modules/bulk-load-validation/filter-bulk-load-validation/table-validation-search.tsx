import React from 'react';
import {
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableRow,
    Box,
    IconButton,
    Tooltip,
    CircularProgress,
    TableFooter,
    TablePagination
} from '@mui/material';
import { useTranslation } from 'react-i18next';
import { Paid as PaidIcon, Visibility as VisibilityIcon } from '@mui/icons-material/';
import './table-validation-search.scss';
import { useNavigate } from 'react-router-dom';
import TablePaginationActions from '../../../shared/pagination/TablePaginationActions';

const TableValidationSearch = ({ isLoading, fileData, page, handlePage, pageSize, totalElements }: any) => {
    const { t } = useTranslation();

    const navigate = useNavigate();

    const handleLogValidation = (idSCV: number) => {
        navigate('logDisplay/' + idSCV);
    };

    const handleChangePage = (event: React.MouseEvent<HTMLButtonElement> | null, currenPage: number) => {
        handlePage(currenPage);
    };

    if (isLoading) {
        return (
            <div className="flex-justify-content_center">
                <CircularProgress id="rmt-loadingPrograms" color="success" />
            </div>
        );
    }

    return (
        <>
            <TableContainer className="table__price_list" component={Paper}>
                <Table>
                    <TableBody className="table__body">
                        <TableRow className="table__massValidationSearchHeadLastFiles">
                            <TableCell className="table__courseProgramCell">{t('tableCell.vID')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.vArchive')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.vPending')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.vDate')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.vUser')}</TableCell>
                            <TableCell className="table__courseProgramCell"></TableCell>
                        </TableRow>
                        <TableRow sx={{ boxShadow: 2 }} className="table__mainRow">
                            <TableCell className="table__cellAux">
                                <Table>
                                    <TableBody>
                                        {fileData?.lotes.map((item: any, i: number) => (
                                            <TableRow
                                                className="table__row table__massValidationSearchBodyLastFiles"
                                                hover={true}
                                                sx={{ '& > *': { borderBottom: 'unset' } }}
                                                key={i}
                                            >
                                                <TableCell className="table__courseProgramCellBody">{item.id}</TableCell>
                                                <TableCell className="table__courseProgramCellBody">{item.nombreArchivo}</TableCell>
                                                <TableCell className="table__courseProgramCellBody">
                                                    {item.countPendientes}/ {item.countTotal}
                                                </TableCell>
                                                <TableCell className="table__courseProgramCellBody">{item.fechaInicio}</TableCell>
                                                <TableCell className="table__courseProgramCellBody">{item.usuario}</TableCell>
                                                <TableCell className="table__courseProgramCellBody">
                                                    {
                                                        <Box className="tableIcons">
                                                            <Tooltip title={t('tooltips.vVisualize')} placement="top" arrow>
                                                                <IconButton aria-label="visibility" onClick={() => handleLogValidation(item.id)}>
                                                                    <VisibilityIcon />
                                                                </IconButton>
                                                            </Tooltip>
                                                            <Tooltip title={t('tooltips.vPrices')} placement="top" arrow>
                                                                <IconButton aria-label="paid">
                                                                    <PaidIcon />
                                                                </IconButton>
                                                            </Tooltip>
                                                        </Box>
                                                    }
                                                </TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableCell>
                        </TableRow>
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

export default TableValidationSearch;
