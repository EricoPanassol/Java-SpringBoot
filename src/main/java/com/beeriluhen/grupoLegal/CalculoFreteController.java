package com.beeriluhen.grupoLegal;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculoFrete")
public class CalculoFreteController {
    private Map<String, CustoBasicoTransporteDTO> cidadesAtendidas;

    public CalculoFreteController() {
        cidadesAtendidas = new TreeMap<>();
        // Define o custo do transporte a partir de São Paulo
        cidadesAtendidas.put("Porto Alegre", new CustoBasicoTransporteDTO("Porto Alegre", 25));
        cidadesAtendidas.put("Florianópolis", new CustoBasicoTransporteDTO("Florianópolis", 20));
        cidadesAtendidas.put("Curitiba", new CustoBasicoTransporteDTO("Curitiba", 15));
        cidadesAtendidas.put("São Paulo", new CustoBasicoTransporteDTO("São Paulo", 10));
    }

    @GetMapping("/cidadesAtendidas")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<String>> consultaCidadesAtendidas() {
        List<String> cidades = new LinkedList<>(cidadesAtendidas.keySet());
        return ResponseEntity.status(HttpStatus.OK).body(cidades);
    }

    @GetMapping("/validaCEP/{umCEP}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> validaCEP(@PathVariable("umCEP") String umCEP) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Que cidade tem este CEP? -> " + umCEP);
    }

    @PostMapping("/custoEntrega")
    @CrossOrigin(origins = "*")
    public ResponseEntity<CustoTempoEntregaDTO> custoEntrega(@RequestBody final SolicitaCustoDTO solCusto) {
        CustoTempoEntregaDTO custoEntrega = new CustoTempoEntregaDTO(
                "01031970", "São Paulo",
                solCusto.cepDestino(), "",
                solCusto.peso(), 0, 0,
                0, 0, -1);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(custoEntrega);
    }
}