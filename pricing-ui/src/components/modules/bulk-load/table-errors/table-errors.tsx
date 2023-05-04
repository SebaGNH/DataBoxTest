import React from 'react';
import { Box, Typography, Paper, Table, TableBody, TableCell, TableContainer, TableRow } from '@mui/material';

const TableErrors = ({ errosData }: any) => {
    return (
        <>
            <Box sx={{ mt: 5 }} className="table_bulk_load">
                <Typography className="table_title" data-testid="table-title" color="primary" gutterBottom>
                    Registros
                </Typography>
            </Box>
            <TableContainer className="table__price_list" component={Paper}>
                <Table sx={{ minWidth: 850 }}>
                    <TableBody className="table__body">
                        <TableRow className="table_HeadFileErrors">
                            <TableCell className="table__courseProgramCell">Resultado</TableCell>
                            <TableCell className="table__courseProgramCell">Linea</TableCell>
                            <TableCell className="table__courseProgramCell">Modalidad</TableCell>
                            <TableCell className="table__courseProgramCell">Tipo de Modalidad</TableCell>
                            <TableCell className="table__courseProgramCell">Ticket</TableCell>
                            <TableCell className="table__courseProgramCell">Arancel</TableCell>
                            <TableCell className="table__courseProgramCell">Precio</TableCell>
                        </TableRow>
                        <TableRow sx={{ boxShadow: 2 }} className="table__mainRow">
                            <TableCell className="table__cellAux">
                                <Table>
                                    <TableBody>
                                        {errosData?.map((item: any, i: number) => (
                                            <TableRow
                                                className="table__row table_BodyFileErrors"
                                                hover={true}
                                                sx={{ '& > *': { borderBottom: 'unset' } }}
                                                key={i}
                                            >
                                                <TableCell className="table__courseProgramCellBody">{item?.errores?.toString()}</TableCell>
                                                <TableCell className="table__courseProgramCellBody">{item.numeroLinea}</TableCell>
                                                <TableCell className="table__courseProgramCellBody">{item.modalidad}</TableCell>
                                                <TableCell className="table__courseProgramCellBody">{item.modalidadTipo}</TableCell>
                                                <TableCell className="table__courseProgramCellBody">{item.tipoTicket}</TableCell>
                                                <TableCell className="table__courseProgramCellBody">{item.tipoArancel}</TableCell>
                                                <TableCell className="table__courseProgramCellBody">{item.precio}</TableCell>
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

export default TableErrors;
