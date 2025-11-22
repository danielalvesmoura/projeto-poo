package dao;

import java.util.List;

public interface DAOGenerico<T, ID> {
    T inserir(T objeto);
    T alterar(T objeto);
    T buscarPorId(ID T);
    //List<T> buscarTodos(String classe);
    List<T> buscarTodos(Class<T> entityClass);
}
