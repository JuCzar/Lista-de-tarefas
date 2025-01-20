import React, { useState, useEffect } from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

const API_URL = 'http://localhost:8080/api/tasks';

function App() {
    const [tasks, setTasks] = useState([]);
    const [taskTitle, setTaskTitle] = useState('');
    const [taskDescription, setTaskDescription] = useState('');
    const [taskStatus, setTaskStatus] = useState('NAO_INICIADO');
    const [taskPriority, setTaskPriority] = useState('BAIXA');
    const [editingTask, setEditingTask] = useState(null);

    
    useEffect(() => {
        fetchTasks();
    }, []);

    const fetchTasks = async () => {
        try {
            const response = await fetch(API_URL);
            const data = await response.json();
            setTasks(data);
        } catch (error) {
            console.error('Erro ao carregar tarefas:', error);
        }
    };

    const handleAddTask = async (e) => {
        e.preventDefault();
        const newTask = { title: taskTitle, description: taskDescription, status: taskStatus, priority: taskPriority };
        try {
            const response = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(newTask),
            });
            if (response.ok) {
                fetchTasks();
                setTaskTitle('');
                setTaskDescription('');
            } else {
                console.error('Erro ao adicionar tarefa');
            }
        } catch (error) {
            console.error('Erro ao adicionar tarefa:', error);
        }
    };

    const handleEditTask = (task) => {
        setEditingTask(task);
        setTaskTitle(task.title);
        setTaskDescription(task.description);
        setTaskStatus(task.status);
        setTaskPriority(task.priority);
    };

    const handleUpdateTask = async (e) => {
        e.preventDefault();
        const updatedTask = { title: taskTitle, description: taskDescription, status: taskStatus, priority: taskPriority };
        try {
            const response = await fetch(`${API_URL}/${editingTask.id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(updatedTask),
            });
            if (response.ok) {
                fetchTasks();
                setTaskTitle('');
                setTaskDescription('');
                setEditingTask(null);  // Limpa a tarefa em edição após atualizar
            } else {
                console.error('Erro ao editar tarefa');
            }
        } catch (error) {
            console.error('Erro ao editar tarefa:', error);
        }
    };

    const handleDeleteTask = async (taskId) => {
        try {
            const response = await fetch(`${API_URL}/${taskId}`, {
                method: 'DELETE',
            });
            if (response.ok) {
                fetchTasks();
            } else {
                console.error('Erro ao excluir tarefa');
            }
        } catch (error) {
            console.error('Erro ao excluir tarefa:', error);
        }
    };

    return (
        <div className="container">
            <h1>Lista de Tarefas</h1>
            <form onSubmit={editingTask ? handleUpdateTask : handleAddTask}>
                <div className="form-group">
                    <label>Título</label>
                    <input
                        type="text"
                        className="form-control"
                        value={taskTitle}
                        onChange={(e) => setTaskTitle(e.target.value)}
                        placeholder="Digite o título da tarefa"
                    />
                </div>
                <div className="form-group">
                    <label>Descrição</label>
                    <textarea
                        className="form-control"
                        value={taskDescription}
                        onChange={(e) => setTaskDescription(e.target.value)}
                        placeholder="Digite a descrição da tarefa"
                    />
                </div>
                <div className="form-group">
                    <label>Status</label>
                    <select className="form-control" value={taskStatus} onChange={(e) => setTaskStatus(e.target.value)}>
                        <option value="NAO_INICIADO">Não Iniciado</option>
                        <option value="EM_ANDAMENTO">Em Andamento</option>
                        <option value="CONCLUIDO">Concluído</option>
                    </select>
                </div>
                <div className="form-group">
                    <label>Prioridade</label>
                    <select className="form-control" value={taskPriority} onChange={(e) => setTaskPriority(e.target.value)}>
                        <option value="BAIXA">Baixa</option>
                        <option value="MEDIA">Média</option>
                        <option value="ALTA">Alta</option>
                    </select>
                </div>
                <button className="btn btn-primary" type="submit">
                    {editingTask ? 'Atualizar Tarefa' : 'Adicionar Tarefa'}
                </button>
            </form>

            <ul className="list-group mt-4">
                {tasks.map((task) => (
                    <li key={task.id} className="list-group-item d-flex justify-content-between align-items-center">
                        <div>
                            <h4>{task.title}</h4>
                            <p>{task.description}</p>
                            <span className={`badge ${task.status === 'CONCLUIDO' ? 'badge-success' : task.status === 'EM_ANDAMENTO' ? 'badge-primary' : 'badge-secondary'}`}>
                                {task.status}
                            </span>
                        </div>
                        <div>
                            <button className="btn btn-warning" onClick={() => handleEditTask(task)}>Editar</button>
                            <button className="btn btn-danger" onClick={() => handleDeleteTask(task.id)}>Excluir</button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default App;
