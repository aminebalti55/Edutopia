package com.example.edutopia_res.Controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController

public class PaymentController {

     /* @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest paymentRequest) {
        // Configuration de l'API Stripe avec votre clé d'API
        Stripe.apiKey = "YOUR_API_KEY";

        // Création d'un objet de charge pour effectuer la transaction de paiement
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", paymentRequest.getAmount()); // Montant à payer
        chargeParams.put("currency", "USD");
        chargeParams.put("source", paymentRequest.getToken()); // Jeton de carte de crédit
        Charge charge;
        try {
            charge = Charge.create(chargeParams);
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        // Mettre à jour la base de données pour refléter le paiement réussi
        paymentService.savePayment(paymentRequest, charge);

        return ResponseEntity.ok("Paiement effectué avec succès.");
    }*/

}
