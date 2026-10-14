package com.sd.trab5.producer;

import com.sd.trab5.common.model.MessagePayload;
import com.sd.trab5.common.model.MessageType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MessageGenerator {

    private final Random random = new Random();

    // Avaliações de Produtos (Positivas, Negativas, Neutras)
    private final List<String> positiveReviews = Arrays.asList(
            "Produto maravilhoso! Chegou super rápido e a qualidade é excelente.",
            "Melhor compra que fiz nos últimos tempos. Superou todas as expectativas!",
            "Muito bom, acabamento de primeira linha e fácil instalação. Recomendo!",
            "Atendeu perfeitamente ao anunciado, excelente custo benefício. Nota 10!",
            "Bateria de longa duração e design moderno. Extremamente satisfeito!",
            "Entrega antes do prazo, produto original e perfeitamente embalado.",
            "Potente, silencioso e muito prático para o dia a dia. Adorei!",
            "Material resistente e de altíssima durabilidade. Vale cada centavo!"
    );

    private final List<String> negativeReviews = Arrays.asList(
            "Péssima qualidade! Quebrou no segundo dia de uso, material frágil.",
            "Não comprem, veio com defeito e o botão de ligar não funciona.",
            "Horrível, som chiado e distorcido. Dinheiro jogado no lixo!",
            "Atrasou mais de três semanas e quando chegou era a cor errada.",
            "Muito ruim, o produto esquenta excessivamente e desliga sozinho.",
            "Decepção total, não tem nada a ver com as fotos da descrição.",
            "Material de plástico barato que quebrou na primeira tentativa de encaixe.",
            "Veio com peças faltando e o suporte não respondeu minha solicitação."
    );

    private final List<String> neutralReviews = Arrays.asList(
            "Produto dentro do esperado pelo valor pago, cumpre o básico.",
            "Chegou no prazo correto, embalagem padrão e produto em ordem.",
            "Funciona normalmente, nem muito bom nem muito ruim.",
            "Qualidade intermediária, razoável para uso doméstico comum.",
            "Dentro da média da categoria, atende para tarefas simples.",
            "Recebi tudo certo, vou testar por mais semanas para verificar a durabilidade.",
            "Condiz com o preço promocional, acabamento mediano.",
            "Nem me surpreendeu e nem me decepcionou, atende o prometido."
    );

    // Solicitações de Suporte (Acesso, Desempenho, Conexão)
    private final List<String> accessIssues = Arrays.asList(
            "Não consigo realizar login no portal, informa que o usuário ou senha são inválidos.",
            "Esqueci minha senha e o e-mail de recuperação de acesso não chega na minha caixa de entrada.",
            "Minha conta corporativa foi bloqueada após três tentativas incorretas de autenticação.",
            "Recebendo erro 403 Forbidden ao tentar visualizar o módulo financeiro no dashboard.",
            "Solicito liberação de permissão para acessar os relatórios gerenciais da equipe.",
            "O token do aplicativo de autenticação em dois fatores (2FA) está dando código inválido.",
            "Não consigo redefinir minhas credenciais pelo portal de autoatendimento.",
            "Sessão expirando automaticamente logo após efetuar o login no sistema."
    );

    private final List<String> performanceIssues = Arrays.asList(
            "O sistema está extremamente lento ao gerar relatórios mensais em PDF.",
            "A tela de listagem de pedidos congela e a aplicação trava com frequência.",
            "Demora mais de quatro minutos para abrir a consulta detalhada de estoque.",
            "Alto consumo de memória RAM travando os navegadores dos atendentes.",
            "Lentidão generalizada após a publicação da última versão no ambiente de produção.",
            "O servidor está batendo 100% de uso de CPU durante o processamento das faturas.",
            "Requisições de busca estão demorando muito tempo para retornar os resultados.",
            "Gargalo grave e lentidão no processamento dos lotes noturnos da empresa."
    );

    private final List<String> connectionIssues = Arrays.asList(
            "Erro ao conectar com o banco de dados principal: Connection refused na porta 5432.",
            "Queda intermitente de conexão com o servidor de backend da nuvem.",
            "Falha de resolução de DNS ao tentar comunicar com a API do gateway de pagamento.",
            "Socket closed inesperadamente durante a sincronização de arquivos com o storage.",
            "Servidor remoto inalcançável: Network is unreachable ao tentar chamada HTTP.",
            "Perda constante de pacotes e alta latência na conexão de rede entre os nós.",
            "Erro de Connection timeout ao tentar autenticar no webhook de terceiros.",
            "Falha no handshake TLS/SSL ao estabelecer conexão segura com o parceiro bancário."
    );

    public MessagePayload generate(boolean simulateError) {
        if (simulateError) {
            MessageType type = random.nextBoolean() ? MessageType.REVIEW : MessageType.SUPPORT;
            return MessagePayload.create(
                    type,
                    "⚠️ [TESTE_DLQ_ERRO_SIMULADO] Erro intencional para validação da Dead Letter Queue!",
                    true
            );
        }

        boolean isReview = random.nextBoolean();
        if (isReview) {
            String content = pickReviewContent();
            return MessagePayload.create(MessageType.REVIEW, content, false);
        } else {
            String content = pickSupportContent();
            return MessagePayload.create(MessageType.SUPPORT, content, false);
        }
    }

    private String pickReviewContent() {
        int r = random.nextInt(3);
        if (r == 0) {
            return positiveReviews.get(random.nextInt(positiveReviews.size()));
        } else if (r == 1) {
            return negativeReviews.get(random.nextInt(negativeReviews.size()));
        } else {
            return neutralReviews.get(random.nextInt(neutralReviews.size()));
        }
    }

    private String pickSupportContent() {
        int r = random.nextInt(3);
        if (r == 0) {
            return accessIssues.get(random.nextInt(accessIssues.size()));
        } else if (r == 1) {
            return performanceIssues.get(random.nextInt(performanceIssues.size()));
        } else {
            return connectionIssues.get(random.nextInt(connectionIssues.size()));
        }
    }
}
