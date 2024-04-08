package br.com.devmedia.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Size;

//mapeamento ORM
// @Entity informamos que essa classe representa uma entidade
@Entity
//@Table informa no banco de dados deve ser mapeada mapeada como uma tabela com nome de musicas
@Table(name = "musicas")
public class Musica {

    @Id // representa o identificadoe unico dessa entidade 
    
    @GeneratedValue(strategy = GenerationType.IDENTITY) // o valor desse atributo sera gerado pelo banco de dados atraves de uma coluna de autoincremento
    private long id;

    @NotBlank 
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String titulo;

    @NotBlank // validação não pode ser vazio
    @Size(min = 2, max = 50) // validação tamanho não pode ser menor que 2 e maior que 50
    @Column(nullable = false, length = 50) // indica ao banco de dados que a coluna relacionada a esse atributo não pode ser nula e precisa ter um tamanho de até 50 caracteres
    private String banda;

    @Range(min = 0, max = 10)
    @Column(nullable = false)
    private int nota;

    // indica que muitas musicas podem pertencer há uma playlist e que o atributo playlist será representado por uma coluna de nome id_playlist_fk na tabela músicas no banco de dados 
    // exatamente como uma chave estrangeira que contera o identificador unico fa playlist da qual aquela musica faz parte
    // playlist foi esse atributo que passou para o parametro mappedBy da notação OnetoMany declaro sobe o atributo musicas da classe Playlist
    @ManyToOne
    @JoinColumn(name = "id_playlist_fk")
    private Playlist playlist;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getBanda() {
        return banda;
    }

    public void setBanda(String banda) {
        this.banda = banda;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

}
