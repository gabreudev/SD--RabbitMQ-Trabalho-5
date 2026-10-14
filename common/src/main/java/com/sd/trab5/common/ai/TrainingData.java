package com.sd.trab5.common.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainingData {

    // ==========================================
    // 1. DADOS DE TREINO PARA ANÁLISE DE SENTIMENTO
    // ==========================================
    public static final String SENTIMENT_POSITIVE = "POSITIVA";
    public static final String SENTIMENT_NEGATIVE = "NEGATIVA";
    public static final String SENTIMENT_NEUTRAL = "NEUTRA";

    public static List<String> getSentimentClasses() {
        return Arrays.asList(SENTIMENT_POSITIVE, SENTIMENT_NEGATIVE, SENTIMENT_NEUTRAL);
    }

    public static List<SmileClassifier.Sample> getSentimentTrainingSamples() {
        List<SmileClassifier.Sample> samples = new ArrayList<>();

        // --- POSITIVAS ---
        samples.add(new SmileClassifier.Sample("Produto excelente, superou todas as minhas expectativas!", SENTIMENT_POSITIVE));
        samples.add(new SmileClassifier.Sample("Muito bom, entrega rápida e mercadoria de ótima qualidade.", SENTIMENT_POSITIVE));
        samples.add(new SmileClassifier.Sample("Adorei a compra! Funciona perfeitamente e é muito bonito.", SENTIMENT_POSITIVE));
        samples.add(new SmileClassifier.Sample("Sensacional, melhor produto que comprei neste ano, recomendo muito.", SENTIMENT_POSITIVE));
        samples.add(new SmileClassifier.Sample("Qualidade impecável, design moderno e bateria dura bastante.", SENTIMENT_POSITIVE));
        samples.add(new SmileClassifier.Sample("Excelente custo benefício, produto original e muito bem embalado.", SENTIMENT_POSITIVE));
        samples.add(new SmileClassifier.Sample("Maravilhoso, atendeu perfeitamente ao que eu precisava. Nota dez!", SENTIMENT_POSITIVE));
        samples.add(new SmileClassifier.Sample("Super recomendo! Chegou antes do prazo e funciona com perfeição.", SENTIMENT_POSITIVE));
        samples.add(new SmileClassifier.Sample("Estou extremamente satisfeito com o atendimento e com o item.", SENTIMENT_POSITIVE));
        samples.add(new SmileClassifier.Sample("Aparelho potente, rápido, silencioso e muito eficiente.", SENTIMENT_POSITIVE));
        samples.add(new SmileClassifier.Sample("Material de primeira linha, acabamento perfeito e durável.", SENTIMENT_POSITIVE));
        samples.add(new SmileClassifier.Sample("Espetacular, compra perfeita, parabéns à loja pelo ótimo serviço.", SENTIMENT_POSITIVE));
        samples.add(new SmileClassifier.Sample("Muito feliz com a compra, produto top e de altíssimo padrão.", SENTIMENT_POSITIVE));
        samples.add(new SmileClassifier.Sample("Gostei muito, superou o modelo anterior com ampla vantagem.", SENTIMENT_POSITIVE));
        samples.add(new SmileClassifier.Sample("Perfeito em todos os sentidos, fácil de instalar e usar.", SENTIMENT_POSITIVE));

        // --- NEGATIVAS ---
        samples.add(new SmileClassifier.Sample("Péssima qualidade, quebrou no segundo dia de uso normal.", SENTIMENT_NEGATIVE));
        samples.add(new SmileClassifier.Sample("Não recomendo a ninguém, produto veio com defeito e não liga.", SENTIMENT_NEGATIVE));
        samples.add(new SmileClassifier.Sample("Horrível! Material de plástico barato e muito frágil, decepção.", SENTIMENT_NEGATIVE));
        samples.add(new SmileClassifier.Sample("Pior compra da minha vida, dinheiro jogado no lixo, péssimo.", SENTIMENT_NEGATIVE));
        samples.add(new SmileClassifier.Sample("Atrasou semanas na entrega e ainda veio a cor errada. Muito insatisfeito.", SENTIMENT_NEGATIVE));
        samples.add(new SmileClassifier.Sample("Não comprem, esquenta demais e parou de funcionar do nada.", SENTIMENT_NEGATIVE));
        samples.add(new SmileClassifier.Sample("Muito ruim, som horrível, estourado e com chiado constante.", SENTIMENT_NEGATIVE));
        samples.add(new SmileClassifier.Sample("Produto falsificado e de péssima durabilidade, não vale nada.", SENTIMENT_NEGATIVE));
        samples.add(new SmileClassifier.Sample("Veio quebrado dentro da caixa e o suporte da loja se recusa a trocar.", SENTIMENT_NEGATIVE));
        samples.add(new SmileClassifier.Sample("Bateria viciada, não dura dez minutos, propaganda totalmente enganosa.", SENTIMENT_NEGATIVE));
        samples.add(new SmileClassifier.Sample("Frustração total, parou de funcionar com menos de um mês.", SENTIMENT_NEGATIVE));
        samples.add(new SmileClassifier.Sample("Decepcionante, não tem nada a ver com as fotos divulgadas.", SENTIMENT_NEGATIVE));
        samples.add(new SmileClassifier.Sample("Terrível experiência, atendimento rude e produto defeituoso.", SENTIMENT_NEGATIVE));
        samples.add(new SmileClassifier.Sample("Completamente inútil, estragou na primeira tentativa de uso.", SENTIMENT_NEGATIVE));
        samples.add(new SmileClassifier.Sample("Gastei caro para receber uma mercadoria quebrada e estragada.", SENTIMENT_NEGATIVE));

        // --- NEUTRAS ---
        samples.add(new SmileClassifier.Sample("Produto dentro do esperado pelo valor que paguei.", SENTIMENT_NEUTRAL));
        samples.add(new SmileClassifier.Sample("Chegou no prazo correto, embalagem padrão e sem avarias visíveis.", SENTIMENT_NEUTRAL));
        samples.add(new SmileClassifier.Sample("Funciona normalmente, nada de extraordinário mas cumpre a função básica.", SENTIMENT_NEUTRAL));
        samples.add(new SmileClassifier.Sample("Produto mediano, nem muito bom e nem muito ruim.", SENTIMENT_NEUTRAL));
        samples.add(new SmileClassifier.Sample("Razoável para o uso diário e doméstico simples.", SENTIMENT_NEUTRAL));
        samples.add(new SmileClassifier.Sample("Condiz com o preço cobrado, qualidade intermediária comum.", SENTIMENT_NEUTRAL));
        samples.add(new SmileClassifier.Sample("Recebi conforme o pedido, vou testar por mais semanas para avaliar.", SENTIMENT_NEUTRAL));
        samples.add(new SmileClassifier.Sample("Desempenho aceitável para tarefas comuns do dia a dia.", SENTIMENT_NEUTRAL));
        samples.add(new SmileClassifier.Sample("Nem me surpreendeu e nem me decepcionou, produto padrão.", SENTIMENT_NEUTRAL));
        samples.add(new SmileClassifier.Sample("Cumpre o que promete nas especificações técnicas, nada a mais.", SENTIMENT_NEUTRAL));
        samples.add(new SmileClassifier.Sample("Item básico, acabamento regular sem detalhes especiais.", SENTIMENT_NEUTRAL));
        samples.add(new SmileClassifier.Sample("Mercadoria recebida no prazo estabelecido pelo vendedor.", SENTIMENT_NEUTRAL));
        samples.add(new SmileClassifier.Sample("Uso rotineiro sem novidades, produto compatível com o anúncio.", SENTIMENT_NEUTRAL));
        samples.add(new SmileClassifier.Sample("Dentro da média da categoria, funciona como esperado.", SENTIMENT_NEUTRAL));
        samples.add(new SmileClassifier.Sample("Pacote recebido em ordem, produto operacional sem observações adicionais.", SENTIMENT_NEUTRAL));

        return samples;
    }

    // ==========================================
    // 2. DADOS DE TREINO PARA SUPORTE TÉCNICO
    // ==========================================
    public static final String SUPPORT_ACCESS = "Problema de acesso";
    public static final String SUPPORT_PERFORMANCE = "Problema de desempenho";
    public static final String SUPPORT_CONNECTION = "Problema de conexão";

    public static List<String> getSupportClasses() {
        return Arrays.asList(SUPPORT_ACCESS, SUPPORT_PERFORMANCE, SUPPORT_CONNECTION);
    }

    public static List<SmileClassifier.Sample> getSupportTrainingSamples() {
        List<SmileClassifier.Sample> samples = new ArrayList<>();

        // --- PROBLEMA DE ACESSO ---
        samples.add(new SmileClassifier.Sample("Não consigo fazer login no portal, informa usuário ou senha inválidos.", SUPPORT_ACCESS));
        samples.add(new SmileClassifier.Sample("Esqueci minha senha de acesso e o link de recuperação nunca chega no email.", SUPPORT_ACCESS));
        samples.add(new SmileClassifier.Sample("Minha conta foi bloqueada após tentativas incorretas de login.", SUPPORT_ACCESS));
        samples.add(new SmileClassifier.Sample("Erro 403 Forbidden ao tentar acessar a área restrita do sistema administrativo.", SUPPORT_ACCESS));
        samples.add(new SmileClassifier.Sample("Sem permissão para visualizar este módulo, solicito autorização de acesso.", SUPPORT_ACCESS));
        samples.add(new SmileClassifier.Sample("Código de autenticação em dois fatores 2FA não é enviado para meu celular.", SUPPORT_ACCESS));
        samples.add(new SmileClassifier.Sample("Não consigo redefinir minhas credenciais de usuário corporativo.", SUPPORT_ACCESS));
        samples.add(new SmileClassifier.Sample("Sessão expirando imediatamente após logar no sistema web.", SUPPORT_ACCESS));
        samples.add(new SmileClassifier.Sample("Usuário desativado no Active Directory ao tentar autenticar.", SUPPORT_ACCESS));
        samples.add(new SmileClassifier.Sample("Mensagem de acesso negado ao tentar consultar os relatórios da gerência.", SUPPORT_ACCESS));
        samples.add(new SmileClassifier.Sample("Não consigo entrar na plataforma com minha conta de colaborador.", SUPPORT_ACCESS));
        samples.add(new SmileClassifier.Sample("Falha no login com Google SSO, erro de credencial não autorizada.", SUPPORT_ACCESS));
        samples.add(new SmileClassifier.Sample("Minha senha expirou e a tela de troca de senha não abre.", SUPPORT_ACCESS));
        samples.add(new SmileClassifier.Sample("Perfil de permissões incorreto, estou sem acesso às minhas funções normais.", SUPPORT_ACCESS));
        samples.add(new SmileClassifier.Sample("Erro de autenticação: token de acesso inválido ou expirado.", SUPPORT_ACCESS));

        // --- PROBLEMA DE DESEMPENHO ---
        samples.add(new SmileClassifier.Sample("O sistema está extremamente lento ao gerar relatórios e exportar planilhas.", SUPPORT_PERFORMANCE));
        samples.add(new SmileClassifier.Sample("A tela congela constantemente e o aplicativo trava durante o uso.", SUPPORT_PERFORMANCE));
        samples.add(new SmileClassifier.Sample("Demora mais de cinco minutos para abrir a consulta da tabela de clientes.", SUPPORT_PERFORMANCE));
        samples.add(new SmileClassifier.Sample("Consumo altíssimo de memória RAM travando o navegador do usuário.", SUPPORT_PERFORMANCE));
        samples.add(new SmileClassifier.Sample("Lentidão generalizada no carregamento do dashboard após a última atualização.", SUPPORT_PERFORMANCE));
        samples.add(new SmileClassifier.Sample("Servidor apresentando cem por cento de uso de CPU e resposta demorada.", SUPPORT_PERFORMANCE));
        samples.add(new SmileClassifier.Sample("As requisições demoram muito para responder, causando timeout na página.", SUPPORT_PERFORMANCE));
        samples.add(new SmileClassifier.Sample("Travamento completo do sistema ao processar o fechamento contábil.", SUPPORT_PERFORMANCE));
        samples.add(new SmileClassifier.Sample("Carregamento de dados muito pesado, sistema fica sem responder.", SUPPORT_PERFORMANCE));
        samples.add(new SmileClassifier.Sample("Gargalo no processamento das rotinas batch noturnas com grande atraso.", SUPPORT_PERFORMANCE));
        samples.add(new SmileClassifier.Sample("Desempenho muito lento e queda drástica de velocidade em horários de pico.", SUPPORT_PERFORMANCE));
        samples.add(new SmileClassifier.Sample("A aplicação congela a cada clique e demora para desbloquear a tela.", SUPPORT_PERFORMANCE));
        samples.add(new SmileClassifier.Sample("Consultas no banco de dados estão demorando uma eternidade para retornar.", SUPPORT_PERFORMANCE));
        samples.add(new SmileClassifier.Sample("Lentidão extrema na tela de busca por produtos cadastrados.", SUPPORT_PERFORMANCE));
        samples.add(new SmileClassifier.Sample("Memória estourando com erro de OutOfMemory e lentidão crônica.", SUPPORT_PERFORMANCE));

        // --- PROBLEMA DE CONEXÃO ---
        samples.add(new SmileClassifier.Sample("Erro ao conectar com o banco de dados remoto: Connection refused.", SUPPORT_CONNECTION));
        samples.add(new SmileClassifier.Sample("Queda constante de conexão com o servidor da aplicação em nuvem.", SUPPORT_CONNECTION));
        samples.add(new SmileClassifier.Sample("Falha de resolução DNS ao tentar comunicar com a API do gateway de pagamento.", SUPPORT_CONNECTION));
        samples.add(new SmileClassifier.Sample("Socket fechado inesperadamente durante o upload de anexos no servidor.", SUPPORT_CONNECTION));
        samples.add(new SmileClassifier.Sample("Servidor inalcançável na rede: Network is unreachable.", SUPPORT_CONNECTION));
        samples.add(new SmileClassifier.Sample("Perda de pacotes e alta latência na conexão de rede entre os servidores.", SUPPORT_CONNECTION));
        samples.add(new SmileClassifier.Sample("Erro de timeout na conexão com o webhook externo de notificações.", SUPPORT_CONNECTION));
        samples.add(new SmileClassifier.Sample("Falha no handshake SSL TLS ao tentar conectar no serviço seguro.", SUPPORT_CONNECTION));
        samples.add(new SmileClassifier.Sample("Conexão interrompida e resetada pelo peer remoto Connection reset.", SUPPORT_CONNECTION));
        samples.add(new SmileClassifier.Sample("Não consigo pingar o host de destino, rota de rede indisponível.", SUPPORT_CONNECTION));
        samples.add(new SmileClassifier.Sample("Perda de sinal de rede e desconexões contínuas no terminal remoto.", SUPPORT_CONNECTION));
        samples.add(new SmileClassifier.Sample("Erro 504 Gateway Timeout: servidor intermediário não obteve resposta de rede.", SUPPORT_CONNECTION));
        samples.add(new SmileClassifier.Sample("Falha de comunicação TCP com a porta 5672 do servidor de mensageria.", SUPPORT_CONNECTION));
        samples.add(new SmileClassifier.Sample("Interface de rede desconectada ou sem sinal com a rede interna.", SUPPORT_CONNECTION));
        samples.add(new SmileClassifier.Sample("Timeout ao tentar estabelecer conexão com o endpoint de integração.", SUPPORT_CONNECTION));

        return samples;
    }
}
