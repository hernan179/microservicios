package com.carro.service.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carro.service.entidades.Carro;
import com.carro.service.servicios.CarroService;

@RestController
@RequestMapping("/carro")
public class CarroController {
	@Autowired
	private CarroService carroService;

	@GetMapping
	public ResponseEntity<List<Carro>> listarUsuario() {
		List<Carro> carros = carroService.getAll();
		if (carros.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(carros);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Carro> obtenerCarro(@PathVariable("id") int id) {
		Carro usuairo = carroService.getCarroById(id);
		if (usuairo == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuairo);

	}

	@PostMapping
	public ResponseEntity<Carro> guardarCarro(@RequestBody Carro Carro) {
		Carro nvCarro = carroService.save(Carro);

		return ResponseEntity.ok(nvCarro);
	}

	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<Carro>> listarCarrosPorUsuarioId(@PathVariable("usuarioId") int id) {

		List<Carro> listCarros = carroService.byUsuarioId(id);

		if (listCarros.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(listCarros);

	}

}
