package br.com.devmedia.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

//mapeamento ORM
//@Entity informa que essa classe representa uma entidade
@Entity
//@Table informa no banco de dados deve ser mapeada mapeada como uma tabela com nome de playlists
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(min = 2, max = 60)
    @Column(nullable = false, length = 60)
    private String nome;

    @NotBlank
    @Column(nullable = false)
    private String descricao;

    // está informando que uma playlist uma ou muitas musicas com essa anotação está criando o relacionamento 1:N (1paraN)
    // entre as entidades playlist e musica, com parametro mappedBy informamos o nome do atributo da outra ponta do relacionamento 
    // isto é na entidade música que deve apresentar a chave estrangeira no banco de dados, já o parametro cascade
    // com valor CascadeType.ALL define que todas operações executados sobre uma entidade serão propagadas
    // sobre a entidade relacionada, por exemplo ao atualizar uma playlist as músicas a ela relacionadas tambem serao atualizadas 
    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private List<Musica> musicas;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

}
