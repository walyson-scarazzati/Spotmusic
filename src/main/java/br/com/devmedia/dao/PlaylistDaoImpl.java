package br.com.devmedia.dao;

import br.com.devmedia.domain.Playlist;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
//para indicar para o framework que ela trata de um repositorio isto é um componente responsavel pelo acesso aos dados armazenado em algum 
//mecanismo de persistencia, nesse caso um banco de dados, Por que criar uma interface e implementa-la? Ao inves de criar apenas 1 classe com esses metodos 
//o que esta fazendo aqui possibilita algo conhecido programar para interface ao inves de programar para implementação 
// uma recomendação da programação orientada a objetos (POO) dessa forma o código torna menos independente de uma implementação
// facilitando por exemplo de uma classe para outra sem que seja necessário realizar as mudanças maiores no projeto além de facilitar o desenvolvimento
// com essa boa pratica o programador não precisa conhecer detalhes da implementação, precisa conhecer apenas os metodos existentes
// por exemplo para acessar o banco de dados 
@Repository
public class PlaylistDaoImpl implements PlaylistDao {
	
	// quando não utiliza o spring ou framework que utiliza injeção de dependencias em cada um desses metodos é comum ter uma instancia de EntityManager
	// para a partir dela comunicar com o banco de dados 

    @PersistenceContext // com essa anotação estamos deixando para o container do Spring a responsabilidade de gerenciar a dependencia que tem de um EntityManager
    // a partir disso sempre utilizar um metodo desse atributo basta declara em ponto nome do metodo, não precisa preocupar com a criação do EntityManager
    // o Spring tira do programador a preocupação de abrir e fechar uma transação para realizar uma operação 
    private EntityManager em;

    // Uma questão IMPORTANTE NÃO ME LEMBRO DE DECLARAR DEPENDÊNCIA DO HIBERNATE E JPA E NEM DA BIBLIOTECA DE VALIDAÇÃO PARA PODER USAR OS RECURSOS
    // QUE ESTÃO UTILIZANDO , então como essas classes e anotações estão disponiveis no projeto? No pom.xml observando a dependência spring-boot-starter-data-jpa
    // lembrando que as dependencias iniciadas com spring-boot-starter traz consigo uma serie de dependencia tudo isso que está sendo utilizado está empacotado nessa dependencia 
    
    @Override
    public void salvar(Playlist playlist) {
        em.persist(playlist);
    }

    @Override
    public List<Playlist> recuperar() {
        return em.createQuery("select p from Playlist p", Playlist.class).getResultList();
    }

    @Override
    public Playlist recuperarPorID(long id) {
        return em.find(Playlist.class, id);
    }

    @Override
    public void atualizar(Playlist playlist) {
        em.merge(playlist);
    }

    @Override
    public void excluir(long id) {
        em.remove(em.getReference(Playlist.class, id));
    }
}
