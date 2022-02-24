package org.kanban.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class TareaUsuarioDTO {
    private int id;
    private String titulo;
    private String descripcion;
    private int user_id;
    private String nombre;
    private String username;
}