import React, { useEffect, useState } from 'react'
import ClienteService from '../services/ClienteService';
import { Link } from 'react-router-dom';

export const ListClientesComponent = () => {
    // Constante que obtiene los datos del backend y empieza en vacio
    const [clientes , setClientes] = useState([]);
    // Hok para llamar al servicio del api clientes
    useEffect(() =>{
        ClienteService.getAllClientes().then(response => {
            setClientes(response.data);
            console.log(response.data);
        }).catch(error => {
            console.error(error);
        })
    },[])

    const listarCliente = () => {
        ClienteService.getAllClientes().then((response)=>{
            setClientes(response.data);
            console.log(response.data);
        }).catch(error =>{
            console.error(error);
        })
    }

    useEffect(() => {
        listarCliente()
    },[])

    const deleteCliente = (clienteId) => {
        ClienteService.deleteCliente(clienteId).then((response)=>{
            listarCliente();
        }).catch(error => {
            console.error(error);
        })
    }

  return (
    <div className = "container">
        <h2 className="text-center"> Lista de Clientes</h2>
        <Link to='/add-cliente' className='btn btn-primary mb-2'>Agregar Cliente</Link>
        <table className="table table-striped table-bordered">
            <thead>
                <th>Id</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>E-mail</th>
                <th>Acciones</th>
            </thead>
            <tbody>
            {
                clientes.map(
                    cliente =>
                    <tr key = {cliente.id}>
                        <td> { cliente.id } </td>
                        <td> { cliente.nombre } </td>
                        <td> { cliente.apellido } </td>
                        <td> { cliente.email } </td>
                        <td>
                            <Link className='btn btn-info' to = {`/edit-cliente/${cliente.id}`} >
                                Actualizar
                            </Link>

                            <button style={{marginLeft: '10px'}} 
                                    className='btn btn-danger' 
                                    onClick={()=> deleteCliente(cliente.id)}>
                            Eliminar
                            </button>

                        </td>
                    </tr>
                )
            }
            </tbody>
        </table>
    </div>
  )
}

export default ListClientesComponent;