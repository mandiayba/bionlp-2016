package Models;

import Utils.Tokenizer;

import java.util.*;

/**
 * Created by kerata on 28/02/16.
 */
public class Ontology {

    private ArrayList<Tree> dependencyTree;
    private Map<String, Term> terms;
    private Map<String, List<Term>> invertedIndex;

    public Ontology() {
        terms = new HashMap<>();
        invertedIndex = new HashMap<>();
    }

    public Map<String, Term> getTerms() {
        return terms;
    }

    public void addTerm(Term term) {
        List<String> tokens = Tokenizer.tokenizeSentence(term.getName());
        for (Synonym synonym: term.getSynonyms())
            tokens.addAll(Tokenizer.tokenizeSentence(synonym.getDetail()));

        for (String token: tokens) {
            List<Term> posting = invertedIndex.get(token);
            if (posting != null) {
                posting.add(term);
            }
            else {
                posting = new ArrayList<>();
                posting.add(term);
                invertedIndex.put(token, posting);
            }
        }

        terms.put(term.getId(), term);
    }

    public List<Term> getTermsForKeywords(List<String> keywords) {
        List<Term> result = new ArrayList<>();
        for (String keyword: keywords)
            result.addAll(getTermsForKeyword(keyword));
        return result;
    }

    public List<Term> getTermsForKeywords(String[] keywords) {
        List<Term> result = new ArrayList<>();
        for (String keyword: keywords)
            result.addAll(getTermsForKeyword(keyword));
        return result;
    }

    public List<Term> getTermsForKeyword(String keyword) {
        return invertedIndex.get(keyword);
    }

    public Ontology buildDependencyTree() {
        dependencyTree = new ArrayList<>();
        for (Iterator<Term> iterator = terms.values().iterator();iterator.hasNext();) {
            Term term = iterator.next();
            Tree tree = new Tree();
            tree.constructFromLeaf(terms, term);
            dependencyTree.add(tree);
//            System.out.println(tree.toString());
        }
        return this;
    }

    @Override
    public String toString() {
        return String.format("terms: %s\n\ninvertedIndex: %s", terms.toString(), invertedIndex.toString());
    }
}