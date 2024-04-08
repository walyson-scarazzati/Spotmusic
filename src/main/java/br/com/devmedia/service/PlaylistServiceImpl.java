package br.com.devmedia.service;

import br.com.devmedia.dao.PlaylistDao;
import br.com.devmedia.domain.Playlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// programar as regras de negócio 
@Service // uma classse de serviço para o Spring
@Transactional // indica ao Spring que a responsabilidade de iniciar, comitar e rollback é dele que deve gerencias essas ações 
// Por que não anotar o @Transactional no DAO ao inves declarar no Service, anota a classes de serviço por que elas fazem uso do DAO
// por que nos metodos da camada de Serviço é comum fazer varias chamadas aos metodos do DAO para implementar uma regra de negócio
public class PlaylistServiceImpl implements PlaylistService {

    @Autowired // fazendo a injeção de dependencias, note que o tipo desse atributo é interface mas quando o Spring for injetar o objeto 
    // a instancia criada e gerenciada pelo container será de PlaylistDaoImpl, tornando o código menos acoplado
    private PlaylistDao playlistDao;

    @Override
    public void salvar(Playlist playlist) {
        playlistDao.salvar(playlist);
    }

    @Override
    @Transactional(readOnly = true) // indica ao Spring que as transações abertas nesses metodos são apenas para leitura, isto é não haverá tentativa de modificar dados no banco
    public List<Playlist> recuperar() {
        return playlistDao.recuperar();
    }

    @Override
    @Transactional(readOnly = true)
    public Playlist recuperarPorId(long id) {
        return playlistDao.recuperarPorID(id);
    }

    @Override
    public void atualizar(Playlist playlist) {
        playlistDao.atualizar(playlist);
    }

    @Override
    public void excluir(long id) {
        playlistDao.excluir(id);
    }

}

