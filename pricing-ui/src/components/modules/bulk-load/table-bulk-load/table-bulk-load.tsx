import React from 'react';
import { Box, Typography, Paper, Table, TableBody, TableCell, TableContainer, TableRow } from '@mui/material';
import { useTranslation } from 'react-i18next';
import '../bulk-load.scss';

const TableBulkLoad = ({ fileData }: any) => {
    const { t } = useTranslation();

    return (
        <>
            <Box sx={{ mt: 5 }} className="table_bulk_load">
                <Typography className="table_title" data-testid="table-title" color="primary" gutterBottom>
                    {t('titles.blTable')}
                </Typography>
            </Box>
            <TableContainer className="table__price_list" component={Paper}>
                <Table sx={{ minWidth: 850 }}>
                    <TableBody className="table__body">
                        <TableRow className="table__courseProgramHeadLastFiles">
                            <TableCell className="table__courseProgramCell">{t('tableCell.tID')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.tFileName')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.tUser')}</TableCell>
                            <TableCell className="table__courseProgramCell">{t('tableCell.tDate')}</TableCell>
                        </TableRow>
                        <TableRow sx={{ boxShadow: 2 }} className="table__mainRow">
                            <TableCell className="table__cellAux">
                                <Table>
                                    <TableBody>
                                        {fileData?.map((item: any, i: number) => (
                                            <TableRow
                                                className="table__row table__courseProgramBodyLastFiles"
                                                hover={true}
                                                sx={{ '& > *': { borderBottom: 'unset' } }}
                                                key={i}
                                            >
                                                <TableCell className="table__courseProgramCellBody">{item.id}</TableCell>
                                                <TableCell className="table__courseProgramCellBody">{item.nombreArchivo}</TableCell>
                                                <TableCell className="table__courseProgramCellBody">{item.usuario}</TableCell>
                                                <TableCell className="table__courseProgramCellBody">{item.fechaCarga}</TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </TableContainer>
        </>
    );
};

export default TableBulkLoad;
