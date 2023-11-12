package org.develop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class FunkoUpdateDto {

    @Length(min = 3, message = "El nombre debe tener al menos 3 caracteres")
    private String nombre;
    @Min(value = 0, message = "El precio no puede ser negativo")
    private double precio;
    @Min(value = 0, message = "La cantidad no puede ser menor a 0")
    private int cantidad;
    private String imagen;
    private String categoria;

}
